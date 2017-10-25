package entity.IO;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

import config.ClientConfig;
import config.entity.Log;
import data.GameData;
import entity.agrement.CommandID;
import entity.agrement.ICommand;
import entity.player.Player;
import rule.agreement.ConnectCommand;
import rule.agreement.LoginCommand;
import rule.agreement.LoginOutCommand;
import thread.entity.FactoryThread;
import thread.entity.exception.ThreadException;
import tool.ClientTools;
import tool.JsonTools;
import tool.agreement.AgreeMentTools;
import tool.agreement.DataBuffer;

/**
 * 主线程中的输出输入流类
 */
public class MainIO {
	
	public Socket clientSocket;
	public Thread send;
	public Thread receive;
	public OutputStream os;
	public InputStream is;
	
	public MainIO(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			is = clientSocket.getInputStream();
			os = clientSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送信息
	 * @param str
	 */
	public void sendMessage(ICommand iCommand,String str) {
		send = new FactoryThread().newThread(new sendThread(iCommand,str),ClientTools.clientThreadSName);
		send.setUncaughtExceptionHandler(new ThreadException());
		send.start();
	}
	
	/**
	 * 接受信息
	 */
	public void receiveMessage() {
		receive = new FactoryThread().newThread(new receiveThread(),ClientTools.clientThreadRName);
		receive.setUncaughtExceptionHandler(new ThreadException());
		receive.start();
	}
	
	/**
	 * 发送线程
	 */
	public class sendThread implements Runnable{
		
		String str = "";
		ICommand iCommand;
		public sendThread() {
		}
		public sendThread(ICommand iCommand,String str) {
			this.str = str;
			this.iCommand = iCommand;
		}
		public synchronized void run() {
//			while(true) {
				//传输json数据
				try {
					DataBuffer data= createAgreeMentMessage(iCommand,str);
					Log.d("["+Thread.currentThread().getName()+"]="+new String(iCommand.body));
					os.write(data.readByte());
					os.flush();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
//			}
		}
	}
	
	/**
	 * 接收线程
	 */
	public class receiveThread implements Runnable{
		public synchronized void run() {
			while(true) {
				try {
					byte[] b = new byte[45056];
					int len=is.read(b);
//					System.out.println("b="+new String(b));
					DataBuffer data = getAgreeMentMessage(b);
					ICommand iCommand = AgreeMentTools.getICommand(data);
					Log.d("["+Thread.currentThread().getName()+"]="+new String(iCommand.body));
					String dataInfo = new String(iCommand.body);
					//判断是否是登录协议信息
					int commandId = AgreeMentTools.judgeICommand(iCommand);
//					System.out.println("commandId="+commandId);
					if( commandId == CommandID.Login) {
						Player player = (Player) JsonTools.parseJson(dataInfo);
						String userName = player.getPlayerName();
						String password = player.getPassword();
						//进行登录验证
						if(userName!=null && password!=null) {
							if(userName.equals("admin")&&password.equals("admin")) {
								//改变客户端的状态
								ClientTools.setClientLocState(Thread.currentThread().getName(),ClientConfig.LOGININHALL);
								//发送登录成功
								sendMessage(new LoginCommand(), "登录成功");
							}else {
								//发送登录失败
								sendMessage(new LoginCommand(),"登录失败");
							}
						}
					}else if(commandId == CommandID.LoginOut) {
						if(dataInfo.equals("退出登录")) {
							ClientTools.setClientLocState(Thread.currentThread().getName(),ClientConfig.NOLOGIN);
							//发送退出登录成功
							sendMessage(new LoginOutCommand(),"退出成功");
						}else {
							//发送退出登录失败
							sendMessage(new LoginOutCommand(),"退出失败");
						}
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 创建协议信息
	 * @param iCommand
	 * @param str
	 * @return
	 */
	public DataBuffer createAgreeMentMessage(ICommand iCommand,String str){
		DataBuffer data = new DataBuffer();
		iCommand.WriteToBuffer(data,str);
		return data;
	}
	
	/**
	 * 接受协议信息
	 * @param bytes
	 * @return
	 */
	public DataBuffer getAgreeMentMessage(byte[] bytes) {
		DataBuffer data = new DataBuffer();
		data.getChars(bytes);
		return data;
	}

}

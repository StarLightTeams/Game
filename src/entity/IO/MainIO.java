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
import entity.client.ClientData;
import entity.player.Player;
import main.TimeServerHandlerExecute;
import rule.agreement.ConnectCommand;
import rule.agreement.GuestLoginCommand;
import rule.agreement.HeartCommand;
import rule.agreement.LoginCommand;
import rule.agreement.LoginOutCommand;
import rule.agreement.RegisterCommand;
import thread.entity.FactoryThread;
import thread.entity.exception.ThreadException;
import tool.ClientTools;
import tool.JsonTools;
import tool.agreement.AgreeMentTools;
import tool.agreement.DataBuffer;

/**
 * ���߳��е������������
 */
public class MainIO {
	
	public Socket clientSocket;
	public Thread send;
	public Thread receive;
	public Thread heart_thread;
	public OutputStream os;
	public InputStream is;
	public TimeServerHandlerExecute singleExecutor;
	//��������Э���ʱ��
	public int time_tocount = 10;
	//��������-����ʱ��(����ʱ��û�з���������˵���ͻ��˶Ͽ�)
	public final int MAX_TIME_END_COUNT =30;
	public int timecount=0;
	
	public MainIO(Socket clientSocket,TimeServerHandlerExecute singleExecutor) {
		this.clientSocket = clientSocket;
		this.singleExecutor = singleExecutor;
		try {
			is = clientSocket.getInputStream();
			os = clientSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������Ϣ
	 * @param str
	 */
	public void sendMessage(ICommand iCommand,String str) {
//		send = new FactoryThread().newThread(new sendThread(iCommand,str),ClientTools.clientThreadSName);
//		send.setUncaughtExceptionHandler(new ThreadException());
//		send.start();
		singleExecutor.excute(new sendThread(iCommand, str,ClientTools.clientThreadSName));
	}
	
	/**
	 * ������Ϣ
	 */
	public void receiveMessage() {
//		receive = new FactoryThread().newThread(new receiveThread(),ClientTools.clientThreadRName);
//		receive.setUncaughtExceptionHandler(new ThreadException());
//		receive.start();
		singleExecutor.excute(new receiveThread(ClientTools.clientThreadRName));
	}
	
	/**
	 * �����߳�
	 */
	public class sendThread implements Runnable{
		
		String str ;
		ICommand iCommand;
		String clientThreadRName;
		public sendThread() {
		}
		public sendThread(ICommand iCommand,String str,String clientThreadRName) {
			this.str = str;
			this.iCommand = iCommand;
			this.clientThreadRName = clientThreadRName;
		}
		public sendThread(ICommand iCommand,String str){
			this.str = str;
			this.iCommand = iCommand;
		}
		public synchronized void run() {
//			while(true) {
				//����json����
				try {
					Thread.currentThread().setName(clientThreadRName);
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
	 * �����߳�
	 */
	public class receiveThread implements Runnable{
		String clientThreadRName;
		public receiveThread() {
			
		}
		
		public receiveThread(String clientThreadRName) {
			this.clientThreadRName = clientThreadRName;
		}
		public synchronized void run() {
			while(true) {
				try {
					Thread.currentThread().setName(clientThreadRName);
					byte[] b = new byte[45056];
					int len=is.read(b);
//					System.out.println("b="+new String(b));
					DataBuffer data = getAgreeMentMessage(b);
					ICommand iCommand = AgreeMentTools.getICommand(data);
					Log.d("["+Thread.currentThread().getName()+"]="+new String(iCommand.body));
					String dataInfo = new String(iCommand.body);
					//�ж��Ƿ��ǵ�¼Э����Ϣ
					int commandId = AgreeMentTools.judgeICommand(iCommand);
					//ˢ�������߳�
					resetHeart();
//					System.out.println("commandId="+commandId);
					if( commandId == CommandID.Login) { //��¼Э��
						Player player = (Player) JsonTools.parseJson(dataInfo);
						String userName = player.getPlayerName();
						String password = player.getPassword();
						int loginState = player.getLoginState();
						//���е�¼��֤
						if(userName!=null && password!=null) {
							//�ı�ͻ��˵�״̬
							ClientTools.setClientLocState(Thread.currentThread().getName(),ClientConfig.LOGININHALL);
							//�ж��û���¼,�ı��û�״̬
							switch(loginState) {
							case ClientConfig.Guest:
								player.setLoginState(ClientConfig.Guest);
								break;
							case ClientConfig.QQ:
								player.setLoginState(ClientConfig.QQ);
								break;
							case ClientConfig.weChat:
								player.setLoginState(ClientConfig.weChat);
								break;
							}
							//�����ݿ����ж��Ƿ����,��Ҫ����
							if(userName.equals("admin")&&password.equals("admin")) {
								//�������Ϣ�������ݿ�,�д�����
								//��player����ClientData
								ClientTools.setClientPlayer(Thread.currentThread().getName(), player);
								//���͵�¼�ɹ�
								sendMessage(new LoginCommand(), "��¼�ɹ�");
							}else {
								//���͵�¼ʧ��
								sendMessage(new LoginCommand(),"��¼ʧ��");
							}
						}
					}else if(commandId == CommandID.LoginOut) {//�˳���¼Э��
						if(dataInfo.equals("�˳���¼")) {
							ClientTools.setClientLocState(Thread.currentThread().getName(),ClientConfig.NOLOGIN);
							//�����˳���¼�ɹ�
							sendMessage(new LoginOutCommand(),"�˳��ɹ�");
						}else {
							//�����˳���¼ʧ��
							sendMessage(new LoginOutCommand(),"�˳�ʧ��");
						}
					}else if(commandId == CommandID.Register) {//ע��Э��
						Player player = (Player) JsonTools.parseJson(dataInfo);
							String userName = player.getPlayerName();
							String password = player.getPassword();
							//����ע����֤
							if(userName!=null && password!=null) {
								//�����ݿ��н����ж��Ƿ����ظ�,������
//								if(true) {
									sendMessage(new RegisterCommand(), "ע��ɹ�");
//								}else {
//									sendMessage(new RegisterCommand(),"ע��ʧ��");
//								}
							}
					}else if(commandId == CommandID.GuestLogin) {
						String GuestName =ClientTools.getGuestPeopleName();
						System.out.println("GuestName="+GuestName+"111111");
						//���浽���ݿ�(������)
						//���ͻ��˷����ο��û�
						sendMessage(new GuestLoginCommand(), GuestName);
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ����Э����Ϣ
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
	 * ����Э����Ϣ
	 * @param bytes
	 * @return
	 */
	public DataBuffer getAgreeMentMessage(byte[] bytes) {
		DataBuffer data = new DataBuffer();
		data.getChars(bytes);
		return data;
	}
	public void startHeartThread(){
		singleExecutor.excute(new HeartThread());
	}
	/*
	 * ˢ�������߳�
	 */
	public void resetHeart()
	{
		time_tocount = 10;
		timecount = 0;
	}
	/*
	 * �����߳�
	 */
	public class HeartThread implements Runnable{
		
		
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				
				time_tocount --;
				if(time_tocount<=0){
					time_tocount = 10;
					sendMessage(new HeartCommand(), "");
				}
				timecount ++;
				if(timecount >= MAX_TIME_END_COUNT){
					//�Ƴ��ͻ���
					
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}

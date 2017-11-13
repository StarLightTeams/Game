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
import java.util.Map;
import java.util.Vector;

import javax.swing.plaf.synth.SynthSpinnerUI;

import org.junit.Test;

import config.ClientConfig;
import config.GameConfig;
import config.RoomConfig;
import config.ServerConfig;
import config.entity.Log;
import data.GameData;
import entity.agrement.CommandID;
import entity.agrement.ICommand;
import entity.client.ClientData;
import entity.info.Info;
import entity.player.Player;
import entity.rooms.Room;
import main.TimeServerHandlerExecute;
import module.DbOperator;
import rule.agreement.ConnectCommand;
import rule.agreement.GameStartCommand;
import rule.agreement.GuestLoginCommand;
import rule.agreement.HeartCommand;
import rule.agreement.LoginCommand;
import rule.agreement.LoginOutCommand;
import rule.agreement.RegisterCommand;
import thread.entity.FactoryThread;
import thread.entity.exception.ThreadException;
import thread.entity.view.ThreadViewerTableModel;
import tool.ClientTools;
import tool.CommonTools;
import tool.DataBaseTools;
import tool.DataTransmitTools;
import tool.GameTools;
import tool.JsonTools;
import tool.RoomTools;
import tool.ThreadTools;
import tool.agreement.AgreeMentTools;
import tool.agreement.DataBuffer;

/**
 * 主线程中的输出输入流类
 */
public class MainIO {
	
	public Socket clientSocket;
	public Runnable send;
	public Runnable receive;
	public Thread heart_thread;
	public OutputStream os;
	public InputStream is;
	public TimeServerHandlerExecute singleExecutor;
	//发送心跳协议的时间
	public int time_tocount = 10;
	//心跳跳动-结束时间(超过时间没有发过包，则说明客户端断开)
	public final int MAX_TIME_END_COUNT =30;
	public int timecount=0;
	public DbOperator dbOperator;
	
	public GameTools gameTools;
	
	public MainIO(Socket clientSocket,TimeServerHandlerExecute singleExecutor) {
		this.clientSocket = clientSocket;
		this.singleExecutor = singleExecutor;
		dbOperator = new DbOperator(new DataBaseTools());
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
	public synchronized void sendMessage(ICommand iCommand,String str) {
//		send = new FactoryThread().newThread(new sendThread(iCommand,str),ClientTools.clientThreadSName);
//		send.setUncaughtExceptionHandler(new ThreadException());
//		send.start();
		send = new sendThread(iCommand, str,ClientTools.clientThreadSName);
		singleExecutor.excute(send);
	}
	
	/**
	 * 接受信息
	 */
	public synchronized void receiveMessage() {
//		receive = new FactoryThread().newThread(new receiveThread(),ClientTools.clientThreadRName);
//		receive.setUncaughtExceptionHandler(new ThreadException());
//		receive.start();
		receive = new receiveThread(ClientTools.clientThreadRName);
		singleExecutor.excute(receive);
	}
	
	/**
	 * 发送线程
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
				//传输json数据
				try {
					Thread.currentThread().setName(clientThreadRName);
					DataBuffer data= createAgreeMentMessage(iCommand,str);
					Log.d("sendMessage = "+str);
					Log.d("["+Thread.currentThread().getName()+"]="+new String(iCommand.body));
					os.write(data.readByte());
					os.flush();
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
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
		String clientThreadRName;
		public receiveThread() {
			
		}
		
		public receiveThread(String clientThreadRName) {
			this.clientThreadRName = clientThreadRName;
		}
		public synchronized void run() {
			while(!Thread.currentThread().isInterrupted()) {
				try {
					Thread.currentThread().setName(clientThreadRName);
					byte[] b = new byte[45056];
					int len=is.read(b);
					System.out.println("len======="+len);
//					System.out.println("b="+new String(b));
					DataBuffer data = getAgreeMentMessage(b);
					ICommand iCommand = AgreeMentTools.getICommand(data);
					Log.d("["+Thread.currentThread().getName()+"]="+new String(iCommand.body));
					String dataInfo = new String(iCommand.body);
					int commandId = AgreeMentTools.judgeICommand(iCommand);
					//刷新心跳线程
//					resetHeart();
//					System.out.println("commandId="+commandId);
					//判断是否是登录协议信息
					if( commandId == CommandID.Login) { //登录协议
						Player player = (Player) JsonTools.parseJson(dataInfo);
						int userId = player.getPlayerNo();
						String userName = player.getPlayerName();
						String password = player.getPassword();
						int loginState = player.getLoginState();
						String clientId = player.getClientId();
						//进行登录验证
						if(userName!=null&& password!=null) {
							boolean flag = loginState!=0?dbOperator.judgePeopleLogin(userName, password, loginState):dbOperator.judgeGuestPeopleLogin(userId, password, loginState);
							if(flag) {
								//在数据库中找到这个用户
								//改变客户端的状态
								ClientTools.setClientLocState(Thread.currentThread().getName(),ClientConfig.LOGININHALL);
								ClientTools.setLoginState(Thread.currentThread().getName(), true);
								//判断用户登录,改变用户状态
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
								case ClientConfig.login:
									player.setLoginState(ClientConfig.login);
									break;
								}
								Log.d(player.toString());
								//把player放入ClientData
								ClientTools.setClientPlayer(Thread.currentThread().getName(), player);
								Log.d(clientSocket.toString());
								//发送登录成功
								sendMessage(new LoginCommand(), JsonTools.getString(new Info("登录成功")));
							}else {
								//此用户不存在
								//发送登录失败
								sendMessage(new LoginCommand(),JsonTools.getString(new Info("登录失败")));
							}
						}
					}else if(commandId == CommandID.LoginOut) {//退出登录协议
						if(dataInfo.equals("退出登录")) {
							ClientTools.setClientLocState(Thread.currentThread().getName(),ClientConfig.NOLOGIN);
							//发送退出登录成功
							sendMessage(new LoginOutCommand(),JsonTools.getString(new Info("退出成功")));
						}else {
							//发送退出登录失败
							sendMessage(new LoginOutCommand(),JsonTools.getString(new Info("退出失败")));
						}
					}else if(commandId == CommandID.Register) {//注册协议
						Player player = (Player) JsonTools.parseJson(dataInfo);
						String userName = player.getPlayerName();
						String password = player.getPassword();
						//进行注册验证
						if(userName!=null && password!=null) {
							//在数据库中进行判断是否有重复
							if(!dbOperator.judgePeopleNameExist(userName)) {
								//没有重复,添加入数据库
								if(dbOperator.insertNewPlayer(userName, password, 4,MainIO.this)) {
									sendMessage(new RegisterCommand(), JsonTools.getString(new Info("注册成功")));
								}else{
									sendMessage(new RegisterCommand(),JsonTools.getString(new Info("注册失败","添加入数据库失败")));
								}
							}else {
								String str = JsonTools.getString(new Info("注册失败","重名"));
								sendMessage(new RegisterCommand(),JsonTools.getString(new Info("注册失败","重名")));
							}
						}
					}else if(commandId == CommandID.GuestLogin) {//游客登录协议
						String GuestName =ClientTools.getGuestPeopleName();
						//保存到数据库
						dbOperator.insertNewPlayer(GuestName, "1", ClientConfig.Guest, MainIO.this);
						String GuestData = dbOperator.getPeopleInfoByName(GuestName);
						//给客户端发送游客用户
						sendMessage(new GuestLoginCommand(), JsonTools.getString(new Info("GuestName",GuestData)));
					}else if(commandId == CommandID.GamePreparing) {//游戏准备
						gameTools = new GameTools(GameData.getSingleton());
						Info info =(Info) JsonTools.parseJson(dataInfo);
						gameTools.gamePreparing(info.dataInfo,MainIO.this,singleExecutor);
					}else if(commandId == CommandID.GameLoading) {//游戏加载
						Info info =(Info) JsonTools.parseJson(dataInfo);
						Map<String, String> maps = JsonTools.parseData(info.dataInfo);
						String roomType = maps.get("roomType");
						String roomId = maps.get("roomId");
						Log.d("roomId="+roomId+",roomType="+roomType);
						//获得玩家的房间
						Room room = GameData.getSingleton().roommap.get(roomType).get(roomId);
						room.roomInfo.endOfLoadingGame++;
						System.out.println("num="+room.roomInfo.endOfLoadingGame);
						if(room.roomInfo.endOfLoadingGame == RoomTools.getRoomPeopleNumByRoomType(roomType)) {
							//改变房间内所有玩家的状态
							DataTransmitTools.changeRoomAllPlayersState(room,RoomConfig.gaming, ClientConfig.GAMESTART, ClientConfig.gaming);
							//开启房间的数据线程
							singleExecutor.excute(new roomThread(room));
							DataTransmitTools.sendAllClientsMessage(room.playermap, new GameStartCommand(), JsonTools.getString(new Info("开始游戏")), singleExecutor);
						}
					}else if(commandId == CommandID.DisConnnect) {//断开连接协议
						Info info = (Info) JsonTools.parseJson(dataInfo);
						System.out.println("info.dataInfo="+info.dataInfo);
						Map<String, String> maps =JsonTools.parseData(info.dataInfo);
						String clientId = maps.get("clientId");
						ClientData clientData = GameData.getSingleton().clientmap.get(clientId);
						//先客户端状态 isLive设置为false
						clientData.getClientSocket().close();
						//在列表汇总移除
						GameData.getSingleton().clientmap.remove(clientId);
						//mainIo
						MainIO mainIo = GameData.getSingleton().mainiomap.get(clientId);
						mainIo.clientSocket.close();
						GameData.getSingleton().mainiomap.remove(clientId);
						//关闭线程
						ThreadTools.remove(clientId);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 房间数据控制线程
	 */
	public class roomThread implements Runnable{
		String roomId;
		String roomType;
		Room room;
		public roomThread() {
			
		}
		
		public roomThread(String roomId,String roomType) {
			this.roomId = roomId;
			this.roomType = roomType;
		}
		public roomThread(Room room) {
			this.room = room;
			this.roomId = room.roomInfo.roomId;
			this.roomType = room.roomInfo.roomType;
		}
		public synchronized void run() {
			while(true) {
				Thread.currentThread().setName(roomId);
				//Log.d("["+Thread.currentThread().getName()+"]="+new String("房间线程开启"));
				if(roomType == RoomTools.createRoomID(2, GameConfig.doubleCommonGame)) {
					
				}else if(roomType == RoomTools.createRoomID(2, GameConfig.doubleSpecialGame)) {
					
				}else if(roomType == RoomTools.createRoomID(4, GameConfig.fourCommonGame)) {
					
				}else if(roomType == RoomTools.createRoomID(4, GameConfig.fourSpecialGame)) {
					
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
	public void startHeartThread(){
		singleExecutor.excute(new HeartThread());
	}
	/*
	 * 刷新心跳线程
	 */
	public void resetHeart()
	{
		time_tocount = 10;
		timecount = 0;
	}
	/*
	 * 心跳线程
	 */
	public class HeartThread implements Runnable{
		
		
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				time_tocount --;
				if(time_tocount<=0){
					time_tocount = 10;
					sendMessage(new HeartCommand(), JsonTools.getString(new Info("")));
				}
				timecount ++;
				if(timecount >= MAX_TIME_END_COUNT){
					//移除客户机
					
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
	
	@Test
	public void test() {
		resetHeart();
		System.out.println("time_tocount="+time_tocount);
		System.out.println("timecount="+timecount);
		new Thread(new HeartThread()).start();
	}

	@Override
	public String toString() {
		return "MainIO [clientSocket=" + clientSocket + ", send=" + send + ", receive=" + receive + ", heart_thread="
				+ heart_thread + ", os=" + os + ", is=" + is + ", singleExecutor=" + singleExecutor + ", time_tocount="
				+ time_tocount + ", MAX_TIME_END_COUNT=" + MAX_TIME_END_COUNT + ", timecount=" + timecount
				+ ", dbOperator=" + dbOperator + ", gameTools=" + gameTools + "]";
	}
	
}

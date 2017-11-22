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
import java.util.HashMap;
import java.util.List;
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
import gameType.chuachua.entity.Ball;
import gameType.chuachua.entity.Board;
import gameType.chuachua.entity.Brick;
import gameType.chuachua.entity.Game;
import gameType.chuachua.entity.bfbData.BfbBall;
import gameType.chuachua.entity.bfbData.BfbBoard;
import gameType.chuachua.entity.bfbData.BfbBrick;
import gameType.chuachua.tools.ConverTool;
import main.TimeServerHandlerExecute;
import module.DbOperator;
import rule.agreement.ConnectCommand;
import rule.agreement.GameDataCommand;
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
 * ���߳��е������������
 */
public class MainIO {
	
	public Socket clientSocket;
	public Runnable send;
//	public Runnable receive;
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
	 * ������Ϣ
	 * @param str
	 */
	public void sendMessage(ICommand iCommand,String str) {
//		send = new FactoryThread().newThread(new sendThread(iCommand,str),ClientTools.clientThreadSName);
//		send.setUncaughtExceptionHandler(new ThreadException());
//		send.start();
		Runnable send = new sendThread(iCommand, str,ClientTools.clientThreadSName);
		singleExecutor.excute(send);
	}
	
	/**
	 * ������Ϣ
	 */
	public synchronized void receiveMessage() {
//		receive = new FactoryThread().newThread(new receiveThread(),ClientTools.clientThreadRName);
//		receive.setUncaughtExceptionHandler(new ThreadException());
//		receive.start();
		Runnable receive = new receiveThread(ClientTools.clientThreadRName);
//		singleExecutor.excute(receive);
		new Thread(receive).start();
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
		public void run() {
			Thread.currentThread().setName(clientThreadRName);
			//����json����
			try {
				DataBuffer data= createAgreeMentMessage(iCommand,str);
				Log.d("sendMessage = "+str);
				Log.d("["+Thread.currentThread().getName()+"]="+new String(iCommand.body));
				os.write(data.readByte());
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			Thread.currentThread().setName(clientThreadRName);
			while(!Thread.currentThread().isInterrupted()) {
				try {
					byte[] b = new byte[45056];
					int len=is.read(b);
					System.out.println("thiscccccccccccccccccccccccc="+Thread.currentThread().getName());
					System.out.println("len======="+len);
//					System.out.println("b="+new String(b));
					DataBuffer data = getAgreeMentMessage(b);
					ICommand iCommand = AgreeMentTools.getICommand(data);
					Log.d("["+Thread.currentThread().getName()+"]="+new String(iCommand.body));
					String dataInfo = new String(iCommand.body);
					int commandId = AgreeMentTools.judgeICommand(iCommand);
					//ˢ�������߳�
//					resetHeart();
//					System.out.println("commandId="+commandId);
					//�ж��Ƿ��ǵ�¼Э����Ϣ
					if( commandId == CommandID.Login) { //��¼Э��
						Player player = (Player) JsonTools.parseJson(dataInfo);
						int userId = player.getPlayerNo();
						String userName = player.getPlayerName();
						String password = player.getPassword();
//						System.out.println("password----------="+password);
						int loginState = player.getLoginState();
						String clientId = player.getClientId();
//						Log.d("clientId--------------- = "+clientId);
						//���е�¼��֤
						if(userName!=null&& password!=null) {
							boolean flag = loginState!=0?dbOperator.judgePeopleLogin(userName, password, loginState):dbOperator.judgeGuestPeopleLogin(userId, password, loginState);
							if(flag) {
								//�����ݿ����ҵ�����û�
								//�ı�ͻ��˵�״̬
								ClientTools.setClientLocState(Thread.currentThread().getName(),ClientConfig.LOGININHALL);
								ClientTools.setLoginState(Thread.currentThread().getName(), true);
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
								case ClientConfig.login:
									player.setLoginState(ClientConfig.login);
									break;
								}
								Log.d(player.toString());
								//��player����ClientData
								ClientTools.setClientPlayer(Thread.currentThread().getName(), player);
								Log.d(clientSocket.toString());
								//���͵�¼�ɹ�
//								System.out.println("JsonTools.getString(new Info(\"��¼�ɹ�\"))="+JsonTools.getString(new Info("��¼�ɹ�")));
								sendMessage(new LoginCommand(), JsonTools.getString(new Info("��¼�ɹ�")));
								Log.d("------------------------------------------------------------------");
							}else {
								//���û�������
								//���͵�¼ʧ��
								sendMessage(new LoginCommand(),JsonTools.getString(new Info("��¼ʧ��")));
								Log.d("------------------------------------------------------------------");
							}
						}
					}else if(commandId == CommandID.LoginOut) {//�˳���¼Э��
						if(dataInfo.equals("�˳���¼")) {
							ClientTools.setClientLocState(Thread.currentThread().getName(),ClientConfig.NOLOGIN);
							//�����˳���¼�ɹ�
							sendMessage(new LoginOutCommand(),JsonTools.getString(new Info("�˳��ɹ�")));
						}else {
							//�����˳���¼ʧ��
							sendMessage(new LoginOutCommand(),JsonTools.getString(new Info("�˳�ʧ��")));
						}
					}else if(commandId == CommandID.Register) {//ע��Э��
						Player player = (Player) JsonTools.parseJson(dataInfo);
						String userName = player.getPlayerName();
						String password = player.getPassword();
						//����ע����֤
						if(userName!=null && password!=null) {
							//�����ݿ��н����ж��Ƿ����ظ�
							if(!dbOperator.judgePeopleNameExist(userName)) {
								//û���ظ�,��������ݿ�
								if(dbOperator.insertNewPlayer(userName, password, 4,MainIO.this)) {
									sendMessage(new RegisterCommand(), JsonTools.getString(new Info("ע��ɹ�")));
								}else{
									sendMessage(new RegisterCommand(),JsonTools.getString(new Info("ע��ʧ��","��������ݿ�ʧ��")));
								}
							}else {
								String str = JsonTools.getString(new Info("ע��ʧ��","����"));
								sendMessage(new RegisterCommand(),JsonTools.getString(new Info("ע��ʧ��","����")));
							}
						}
					}else if(commandId == CommandID.GuestLogin) {//�ο͵�¼Э��
						String GuestName =ClientTools.getGuestPeopleName();
						String mainIoId = ClientTools.getKeyByThreadName(clientThreadRName);
						//���浽���ݿ�
						dbOperator.insertNewPlayer(GuestName, "1", ClientConfig.Guest, MainIO.this);
						String GuestData = dbOperator.getPeopleInfoByName(GuestName);
						//���ͻ��˷����ο��û�
						sendMessage(new GuestLoginCommand(), JsonTools.getString(new Info("GuestName",GuestData)));
					}else if(commandId == CommandID.GamePreparing) {//��Ϸ׼��
						gameTools = new GameTools(GameData.getSingleton());
						Info info =(Info) JsonTools.parseJson(dataInfo);
						gameTools.gamePreparing(info.dataInfo,MainIO.this,singleExecutor);
						Log.d("--------------------------------------------------------------------");
					}else if(commandId == CommandID.GameLoading) {//��Ϸ����
						System.out.println("GameLoading----------------="+dataInfo);
						Info info =(Info) JsonTools.parseJson(dataInfo);
						System.out.println("--------------");
						Map<String, String> maps = JsonTools.parseData(info.dataInfo);
						String roomType = maps.get("roomType");
						String roomId = maps.get("roomId");
						Log.d("roomId="+roomId+",roomType="+roomType);
						//�����ҵķ���
						Room room = GameData.getSingleton().roommap.get(roomType).get(roomId);
						room.roomInfo.endOfLoadingGame++;
						System.out.println("num="+room.roomInfo.endOfLoadingGame);
						if(room.roomInfo.endOfLoadingGame == RoomTools.getRoomPeopleNumByRoomType(roomType)) {
							//�ı䷿����������ҵ�״̬
							DataTransmitTools.changeRoomAllPlayersState(room,RoomConfig.gaming, ClientConfig.GAMESTART, ClientConfig.gaming);
							//��������������߳�
//							singleExecutor.excute(new roomThread(room));
							DataTransmitTools.sendClientsMessage(room.playermap, new GameStartCommand(), JsonTools.getString(new Info("��ʼ��Ϸ")), singleExecutor);
							Log.d("---------------------------------------------------------------------------------");
						}
					}else if(commandId == CommandID.DisConnnect) {//�Ͽ�����Э��
						Info info = (Info) JsonTools.parseJson(dataInfo);
						System.out.println("info.dataInfo="+info.dataInfo);
						Map<String, String> maps =JsonTools.parseData(info.dataInfo);
						String clientId = maps.get("clientId");
						ClientData clientData = GameData.getSingleton().clientmap.get(clientId);
						//�ȿͻ���״̬ isLive����Ϊfalse
						clientData.getClientSocket().close();
						//���б�����Ƴ�
						GameData.getSingleton().clientmap.remove(clientId);
						//mainIo
						MainIO mainIo = GameData.getSingleton().mainiomap.get(clientId);
						mainIo.clientSocket.close();
						GameData.getSingleton().mainiomap.remove(clientId);
						//�ر��߳�
//						singleExecutor.executeor.shutdown();
						ThreadTools.remove(clientId);
					}else if(commandId == CommandID.GameData) {//��Ϸ����
						Info info = (Info) JsonTools.parseJson(dataInfo);
//						System.out.println("iioooioioioooooooi="+info.dataInfo);
						Map<String, String> maps =JsonTools.pasreObjectData(info.dataInfo);
						String roomType = maps.get("roomType");
						String roomId = maps.get("roomId");
						String clientId = maps.get("clientName");
						int gameWidth = Integer.parseInt(maps.get("clientSceenWidth"));
						int gameHeight = Integer.parseInt(maps.get("clientSceenHeight"));
						System.out.println("clientId="+clientId);
						String gameData = maps.get("Game");
						CommonTools.listMaps(maps);
						Game game = (Game) JsonTools.parseJson(gameData);
//						System.out.println("game.myborad="+game.myborad.toString());
//						System.out.println("game.enemyborad="+game.enemyborad.toString());
						//ת�����λ��
//						game.enemyborad.setLocX(game.myborad.locX);
//						System.out.println("game.myborad111111="+game.myborad.toString());
//						System.out.println("game.enemyborad11111="+game.enemyborad.toString());
						
//						System.out.println(game.toString());
						roomThread roomExe = new roomThread(roomId,roomType,clientId,game,gameWidth,gameHeight);
//						new Thread(new roomThread(roomId,roomType,clientId,game,gameWidth,gameHeight)).start();
						singleExecutor.excute(roomExe);
						
//						//�ҵ������������
//						List<Player> players = DataTransmitTools.getOtherPlayerInRoom(roomId, roomType, clientId);
//						for(Player p :players) {
//							System.out.println(p.toString());
//						}
//						Map<String,String> mmaps = new HashMap<String, String>();
//						mmaps.put("roomType", roomType);
//						mmaps.put("roomId", roomId);
//						mmaps.put("clientName", clientId);
//						mmaps.put("Game",JsonTools.getString(game));
//						CommonTools.listMaps(maps);
//						
//						//������������˷�����Ϣ                 
//						DataTransmitTools.sendClientsMessage(players, new GameDataCommand(), JsonTools.getString(new Info("��Ϸ����",JsonTools.getData(mmaps))), singleExecutor);
						
						Log.d("----------------------------���ݴ���----------------------------------");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * �������ݿ����߳�
	 */
	public class roomThread implements Runnable{
		String roomId;
		String roomType;
		Room room;
		String clientId;
		Game game;
		int gameWidth;
		int gameHeight;
		public roomThread() {
			
		}
		public roomThread(String roomId,String roomType,String clientId,Game game,int gameWidth,int gameHeight) {
			this.roomId = roomId;
			this.roomType = roomType;
			this.clientId = clientId;
			this.game = game;
			this.gameWidth = gameWidth;
			this.gameHeight = gameHeight;
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
//			while(true) {
				Thread.currentThread().setName(roomId);
				Log.d("["+Thread.currentThread().getName()+"]="+new String("�����߳̿���"));
				if(roomType.equals(RoomTools.createRoomType(2, GameConfig.doubleCommonGame))) {
//					List<BfbBall> BfbBalls = ConverTool.conver_ballList(game.ball_list,gameWidth,gameHeight);
//					List<Ball> bs = ConverTool.reduction_ballList(BfbBalls, gameType.chuachua.config.GameConfig.WIDTH, gameType.chuachua.config.GameConfig.HEIGHT);
//					
//					List<BfbBrick> BfbMyBricks = ConverTool.conver_brickList(game.myBrickList,gameWidth,gameHeight);
//					List<Brick> myBk = ConverTool.reduction_brickList(BfbMyBricks,gameType.chuachua.config.GameConfig.WIDTH, gameType.chuachua.config.GameConfig.HEIGHT);
//					
//					List<BfbBrick> BfbEnemyBricks = ConverTool.conver_brickList(game.enemyBrickList,gameWidth,gameHeight);
//					List<Brick> enemyBk = ConverTool.reduction_brickList(BfbEnemyBricks,gameType.chuachua.config.GameConfig.WIDTH, gameType.chuachua.config.GameConfig.HEIGHT);
//					
//					BfbBoard BfbMyBorad = ConverTool.conver_board(game.myborad,gameWidth,gameHeight);
//					Board myBd = ConverTool.reduction_board(BfbMyBorad,gameType.chuachua.config.GameConfig.WIDTH, gameType.chuachua.config.GameConfig.HEIGHT);
//					
//					BfbBoard BfbEnemyBorad = ConverTool.conver_board(game.enemyborad,gameWidth,gameHeight);
//					Board enemyBd = ConverTool.reduction_board(BfbEnemyBorad,gameType.chuachua.config.GameConfig.WIDTH, gameType.chuachua.config.GameConfig.HEIGHT);
//					Game game2 = new Game(bs, myBk, enemyBk, myBd, enemyBd, game.boardPropsmap);
					//�ҵ������������
					List<Player> players = DataTransmitTools.getOtherPlayerInRoom(roomId, roomType, clientId);
					for(Player p :players) {
						System.out.println(p.toString());
					}
					//����ƶ�
					for(int i=0;i<game.ball_list.size();i++) {
						Ball ball = game.ball_list.get(i);
						ball.move(1);
					}
					//����ײ
					
					Map<String,String> mmaps = new HashMap<String, String>();
					mmaps.put("roomType", roomType);
					mmaps.put("roomId", roomId);
					mmaps.put("clientName", clientId);
					mmaps.put("Game",JsonTools.getString(game));
					//������������˷�����Ϣ                 
					DataTransmitTools.sendClientsMessage(players, new GameDataCommand(), JsonTools.getString(new Info("��Ϸ����",JsonTools.getData(mmaps))), singleExecutor);
				
				}else if(roomType.equals(RoomTools.createRoomType(2, GameConfig.doubleSpecialGame))) {
					
				}else if(roomType.equals(RoomTools.createRoomType(4, GameConfig.fourCommonGame))) {
					
				}else if(roomType.equals(RoomTools.createRoomType(4, GameConfig.fourSpecialGame))) {
					
				}
			}
//		}
		
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
					sendMessage(new HeartCommand(), JsonTools.getString(new Info(" ")));
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
	
	@Test
	public void test() {
//		resetHeart();
//		System.out.println("time_tocount="+time_tocount);
//		System.out.println("timecount="+timecount);
//		new Thread(new HeartThread()).start();
	}

	@Override
	public String toString() {
		return "MainIO [clientSocket=" + clientSocket + ", heart_thread=" + heart_thread + ", os=" + os + ", is=" + is
				+ ", singleExecutor=" + singleExecutor + ", time_tocount=" + time_tocount + ", MAX_TIME_END_COUNT="
				+ MAX_TIME_END_COUNT + ", timecount=" + timecount + ", dbOperator=" + dbOperator + ", gameTools="
				+ gameTools + "]";
	}
	
}

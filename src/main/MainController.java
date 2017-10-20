package main;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import rule.agreement.ConnectCommand;
import tool.ClientTools;
import tool.CreateRoomTools;
import tool.agreement.DataBuffer;
import config.GameConfig;
import config.ServerConfig;
import config.entity.Log;
import data.GameData;
import entity.FactoryRooms.FactoryDoubleRoom;
import entity.FactoryRooms.FactoryFourRoom;
import entity.IO.MainIO;
import entity.client.ClientData;
import entity.client.ClientPortData;
import entity.rooms.DoubleRoom;
import entity.rooms.FourRoom;
import entity.rooms.Room;

/**
 *	服务器主类
 */
public class MainController {
	//服务
	ServerSocket server = null;
	//服务器配置
	ServerConfig sc =ServerConfig.getInstance();
	TimeServerHandlerExecute singleExecutor =new TimeServerHandlerExecute(sc.peopleConnectionCount,sc.serverConnectionTime);
	Socket socket =null;
	//获取单例
	GameData g =GameData.getSingleton();
	private boolean startserver() throws Exception{
		initRooms();
		server = new ServerSocket(sc.port);
		Log.d("----------------【服务器开启监控端口:"+sc.port+"】--------------");
		Log.d("----------------【等待客户机链接中】---------------");
		waitConnection();
		return true;
	}

	public void waitConnection(){
		while(true){
			try{
				socket=server.accept();
				//有客户端连接,每收到服务器socket放入客户端列表中
				String ip = socket.getInetAddress().toString().substring(1);
				int port = socket.getPort();
				//如果列表中存在此ip
				ClientPortData clientportdata;
				if(g.userclientmap.containsKey(ip)) {
					clientportdata = g.userclientmap.get(ip);
				}else {
					clientportdata = new ClientPortData();
				}
				
				clientportdata.addPort(port);
				//判断是否到达端口上限
				if(clientportdata.judgePortCount()) {
					g.userclientmap.put(ip, clientportdata);
					if(ClientTools.addClient(socket)) {
						Log.d("客户端:"+ip+":"+port+"添加成功");
						Log.d("正在运行的客户端:");
						for(Map.Entry<String, ClientData> entry:g.clientmap.entrySet()) {
							Log.d("客户端:"+entry.getValue().getIp()+":"+entry.getValue().getPort());
						}
						Log.d("----------------------------------");
						
						//得到当前客户端的接,发线程名
						ClientTools.initClientThreadName(socket);
						//开启接收,发送线程
						MainIO mainIO = new MainIO(g.clientmap.get(ip+":"+port).getClientSocket());
						mainIO.sendMessage("太多太多");
						mainIO.receiveMessage();
						g.mainiomap.put(ip+":"+port,mainIO);
						
					}else {
						Log.d("客户端:"+ip+":"+port+"添加失败");
					}
				}else {
					Log.d("客户端:"+ip+"用户达到上限");
				}
				
				//用户登录
				//将用户添加入房间
				
				
				
			}catch(Exception e){
				e.printStackTrace();
				Log.d("客户端连接发生异常");
			}
		}
	}
	
	/**
	 * 初始化房间
	 */
	public void initRooms(){
		
		//创建两个普通房间列表
		//初始化两个房间
		g.roommap.put(2, new  HashMap<String,Room>());
		g.roommap.put(4, new  HashMap<String,Room>());
		//双人普通房
		for(int i=0;i<GameConfig.doubleRoomCount;i++) {
			String roomId = CreateRoomTools.createRoomID(2, i);
			DoubleRoom dr =new FactoryDoubleRoom().createRoom(roomId);
			CreateRoomTools.insertTable(g.roommap, 2, roomId, dr);
		}
		//四人普通房
		for(int i=0;i<GameConfig.fourRoomCount;i++) {
			String roomId = CreateRoomTools.createRoomID(4, i);
			FourRoom fr =new FactoryFourRoom().createRoom(roomId);
			CreateRoomTools.insertTable(g.roommap, 4, roomId, fr);
		}
		
	}
	
	
	public static void main(String[] args) {
		MainController controller =new MainController();
		try{
			if(controller.startserver()){
				
			}
		}catch(Exception e){
			Log.d("服务器发生错误");
			e.printStackTrace();
		}
	}
	@Test
	public void test(){
		ConnectCommand conect =new ConnectCommand();
		DataBuffer buffer =new DataBuffer();
//		conect.WritheLength(buffer);
		conect.WriteToBuffer(buffer);
		
		byte[] b =buffer.readByte();//分割
		//-------------------------------
		DataBuffer buffer1 =new DataBuffer();
		buffer1.getChars(b);
		conect.ReadFromBuffer(buffer1);
		System.out.println(conect.header.id);
		System.out.println(conect.header.length);
		System.out.print(conect.body);
//		System.out.println("111111111111");
	}
}

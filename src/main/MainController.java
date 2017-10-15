package main;

import java.net.ServerSocket;
import java.net.Socket;

import tool.CreateRoomTools;
import config.GameConfig;
import config.ServerConfig;
import data.GameData;
import entity.FactoryRooms.FactoryDoubleRoom;
import entity.FactoryRooms.FactoryFourRoom;
import entity.rooms.DoubleRoom;
import entity.rooms.FourRoom;



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
		
		server = new ServerSocket(GameConfig.port);
		System.out.println("----------------【服务器开启监控端口:--------------"+GameConfig.port+"】");
		System.out.println("----------------【等待客户机链接中】---------------");
		return true;
	}

	public void waitConnection(){
		while(true){
			try{
				socket=server.accept();
				
			}catch(Exception e){
				System.out.println("客户端连接发生异常");
			}
		}
	}
	
	/**
	 * 初始化房间
	 */
	public void initRooms(){
		
		//创建两个普通房间列表
		
		//双人普通房
		for(int i=0;i<GameConfig.doubleRoomCount;i++) {
			String roomId = CreateRoomTools.createRoomID(2, i);
			DoubleRoom dr =new FactoryDoubleRoom().createRoom(roomId);
			
			g.roommap.put(roomId, dr);
			
		}
		//四人普通房
		for(int i=0;i<GameConfig.fourRoomCount;i++) {
			String roomId = CreateRoomTools.createRoomID(4, i);
			FourRoom fr =new FactoryFourRoom().createRoom(roomId);
			g.roommap.put(roomId, fr);
		}
		
	}
	
	
	public static void main(String[] args) {
		MainController controller =new MainController();
		try{
			if(controller.startserver()){
				
			}
		}catch(Exception e){
			System.out.println("服务器发生错误");
		}
	}
}

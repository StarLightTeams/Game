package main;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import tool.CreateRoomTools;
import config.GameConfig;
import config.ServerConfig;
import data.GameData;
import entity.FactoryRooms.FactoryDoubleRoom;
import entity.FactoryRooms.FactoryFourRoom;
import entity.rooms.DoubleRoom;
import entity.rooms.FourRoom;
import entity.rooms.Room;



public class MainController {
	//����
	ServerSocket server = null;
	//����������
	ServerConfig sc =ServerConfig.getInstance();
	TimeServerHandlerExecute singleExecutor =new TimeServerHandlerExecute(sc.peopleConnectionCount,sc.serverConnectionTime);
	Socket socket =null;
	//��ȡ����
	GameData g =GameData.getSingleton();
	private boolean startserver() throws Exception{
		initRooms();
		server = new ServerSocket(sc.port);
		System.out.println("----------------��������������ض˿�:--------------"+sc.port+"��");
		System.out.println("----------------���ȴ��ͻ��������С�---------------");
		waitConnection();
		return true;
	}

	public void waitConnection(){
		while(true){
			try{
				socket=server.accept();
				
			}catch(Exception e){
				System.out.println("�ͻ������ӷ����쳣");
			}
		}
	}
	
	/**
	 * ��ʼ������
	 */
	public void initRooms(){
		
		//����������ͨ�����б�
		//��ʼ����������
		g.roommap.put(2, new  HashMap<String,Room>());
		g.roommap.put(4, new  HashMap<String,Room>());
		//˫����ͨ��
		for(int i=0;i<GameConfig.doubleRoomCount;i++) {
			String roomId = CreateRoomTools.createRoomID(2, i);
			DoubleRoom dr =new FactoryDoubleRoom().createRoom(roomId);
			CreateRoomTools.insertTable(g.roommap, 2, roomId, dr);
			
		}
		//������ͨ��
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
			System.out.println("��������������");
			e.printStackTrace();
		}
	}
}

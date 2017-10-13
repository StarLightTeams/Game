package main;

import java.net.ServerSocket;
import java.net.Socket;

import config.GameConfig;
import entity.FactoryRooms.FactoryDoubleRoom;



public class MainController {
	//����
	ServerSocket server = null;
	TimeServerHandlerExecute singleExecutor =new TimeServerHandlerExecute(50,1000);
	Socket socket =null;
	
	private boolean startserver() throws Exception{
		
		server = new ServerSocket(GameConfig.port);
		System.out.println("----------------��������������ض˿�:--------------"+GameConfig.port+"��");
		System.out.println("----------------���ȴ��ͻ��������С�---------------");
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
		for(int i=0;i<GameConfig.doubleRoomCount;i++) {
			String roomId = i+"00"+i;
			new FactoryDoubleRoom().createRoom(roomId);
		}
		
	}
	
	
	public static void main(String[] args) {
		MainController controller =new MainController();
		try{
			if(controller.startserver()){
				
			}
		}catch(Exception e){
			System.out.println("��������������");
		}
	}
}

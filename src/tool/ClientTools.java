package tool;

import java.net.Socket;

import config.GameConfig;
import data.GameData;
import entity.client.ClientData;

public class ClientTools {
	
	public static GameData g = GameData.getSingleton();
	/**
	 *��ӿͻ��� 
	 */
	public static boolean addClient(Socket socket) {
		try {
			ClientData gameDate = new ClientData(socket);
			g.clientmap.put(GameConfig.serverCount,gameDate);
			GameConfig.serverCount++;
			return true;
		}catch(Exception e) {
			new Exception("�ͻ���"+socket.getInetAddress()+":"+socket.getPort()+"���ʧ��");
			return false;
		}
	}
}

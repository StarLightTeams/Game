package tool;

import java.net.Socket;

import config.GameConfig;
import data.GameData;
import entity.client.ClientData;

/**
 * �ͻ��˷���
 */
public class ClientTools {
	
	public static GameData g = GameData.getSingleton();
	/**
	 *��ӿͻ��� 
	 */
	public static boolean addClient(Socket socket) {
		try {
			ClientData gameData = new ClientData(socket);
			String flag = gameData.getIp()+":"+gameData.getPort();
			g.clientmap.put(flag,gameData);
			GameConfig.serverCount++;
			return true;
		}catch(Exception e) {
			new Exception("�ͻ���"+socket.getInetAddress()+":"+socket.getPort()+"���ʧ��");
			return false;
		}
	}
}

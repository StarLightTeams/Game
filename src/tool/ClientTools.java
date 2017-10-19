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
	public static String clientThreadName;
	public static String clientThreadRName;
	public static String clientThreadSName;
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
	
	/**
	 * ��ÿͻ��˵��߳���
	 * 	�߳�ip+:+port+:+1s 1��ʾ������ ,2��ʾ�ͻ��� s��ʾ���� r��ʾ����
	 */
	public static void initClientThreadName(Socket s) {
		clientThreadName = s.getInetAddress().toString().substring(1)+":"+s.getPort()+":"+"1";
		clientThreadRName = clientThreadName+"r";
		clientThreadSName = clientThreadName+"s";
	}
}

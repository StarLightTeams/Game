package tool;

import java.net.Socket;

import config.GameConfig;
import data.GameData;
import entity.client.ClientData;

/**
 * 客户端方法
 */
public class ClientTools {
	
	public static GameData g = GameData.getSingleton();
	public static String clientThreadName;
	public static String clientThreadRName;
	public static String clientThreadSName;
	/**
	 *添加客户端 
	 */
	public static boolean addClient(Socket socket) {
		try {
			ClientData gameData = new ClientData(socket);
			String flag = gameData.getIp()+":"+gameData.getPort();
			g.clientmap.put(flag,gameData);
			GameConfig.serverCount++;
			return true;
		}catch(Exception e) {
			new Exception("客户端"+socket.getInetAddress()+":"+socket.getPort()+"添加失败");
			return false;
		}
	}
	
	/**
	 * 获得客户端的线程名
	 * 	线程ip+:+port+:+1s 1表示服务器 ,2表示客户端 s表示发送 r表示接收
	 */
	public static void initClientThreadName(Socket s) {
		clientThreadName = s.getInetAddress().toString().substring(1)+":"+s.getPort()+":"+"1";
		clientThreadRName = clientThreadName+"r";
		clientThreadSName = clientThreadName+"s";
	}
}

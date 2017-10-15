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
}

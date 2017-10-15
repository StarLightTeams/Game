package tool;

import java.net.Socket;

import config.GameConfig;
import data.GameData;
import entity.client.ClientData;

public class ClientTools {
	
	public static GameData g = GameData.getSingleton();
	/**
	 *添加客户端 
	 */
	public static boolean addClient(Socket socket) {
		try {
			ClientData gameDate = new ClientData(socket);
			g.clientmap.put(GameConfig.serverCount,gameDate);
			GameConfig.serverCount++;
			return true;
		}catch(Exception e) {
			new Exception("客户端"+socket.getInetAddress()+":"+socket.getPort()+"添加失败");
			return false;
		}
	}
}

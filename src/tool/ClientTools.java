package tool;

import java.net.Socket;

import config.ClientConfig;
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
			//改变客户端中的状态,未登录
			gameData.setClientLocState(ClientConfig.NOLOGIN);
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
	
	/**
	 * 通过当前线程名来的到列表中的key值
	 * @param ThreadName
	 */
	public static String getKeyByThreadName(String ThreadName) {
		String[] str = ThreadName.split(":");
		return str[0]+":"+str[1];
	}
	
	public static void setClientLocState(String ThreadName,int state) {
		String key = ClientTools.getKeyByThreadName(ThreadName);
		GameData.getSingleton().clientmap.get(key).setClientLocState(ClientConfig.LOGININHALL);
	}
}

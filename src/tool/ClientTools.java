package tool;

import java.net.Socket;
import java.util.Random;

import org.junit.Test;

import config.ClientConfig;
import config.GameConfig;
import data.GameData;
import entity.client.ClientData;
import entity.player.Player;
import module.DbOperator;

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
	
	/**
	 * 设置客户端的状态
	 * @param ThreadName
	 * @param state
	 */
	public static void setClientLocState(String ThreadName,int state) {
		String key = ClientTools.getKeyByThreadName(ThreadName);
		GameData.getSingleton().clientmap.get(key).setClientLocState(ClientConfig.LOGININHALL);
	}
	
	/**
	 * 设置客户端玩家
	 * @param ThreadName
	 * @param player
	 */
	public static void setClientPlayer(String ThreadName,Player player) {
		String key = ClientTools.getKeyByThreadName(ThreadName);
		GameData.getSingleton().clientmap.get(key).setPlayer(player);
	}
	
	/**
	 * 获得游客名字
	 */
	public static String getGuestPeopleName() {
		//字母数组(包含a~z的字母和数字)
		char[] str = new char [26*2+10];
		for(int i=0;i<26;i++) {
			str[i] = (char)(65+i);
		}
		for(int i=0;i<26;i++) {
			str[26+i] = (char)(97+i);
		}
		for(int i=0;i<10;i++) {
			str[52+i] = (i+"").charAt(0);
		}
		DbOperator dbOperator = new DbOperator(new DataBaseTools());
		Random rand = new Random();
		char[] name = new char[8];
		String GuestName;
		do {
			int t;
			for(int i=0;i<8;i++) {
				t=rand.nextInt(62);
				name[i] =str[t]; 
			}
			GuestName = new String(name);
		}while(dbOperator.judgePeopleNameExist(GuestName));
 		return GuestName;
	}
	
	@Test
	public void test() {
		System.out.println(getGuestPeopleName());
	}
}

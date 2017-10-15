package data;

import java.util.HashMap;
import java.util.Map;

import entity.client.ClientData;
import entity.rooms.Room;

/*
 * add by king 
 * 游戏数据类 单例模式
 */
public class GameData {

	// 单例模式 双重校验锁
	private volatile static GameData gamedata;

	private GameData() {
	}

	public static GameData getSingleton() {
		if (gamedata == null) {
			synchronized (GameData.class) {
				if (gamedata == null) {
					gamedata = new GameData();
				}
			}
		}
		return gamedata;
	}
	
	// 房间配置<房间类型，房间列表《房间号，房间数据》>
	public Map<Integer, Map<String,Room>> roommap = new HashMap<Integer, Map<String,Room>>();
	
	//客户端列表<个数,客户端数据>
	public Map<Integer,ClientData> clientmap = new HashMap<Integer,ClientData>();
}

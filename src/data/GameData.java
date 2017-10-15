package data;

import java.util.HashMap;
import java.util.Map;

import entity.client.ClientData;
import entity.client.ClientPortData;
import entity.rooms.Room;

/*
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
	
	//客户端列表<ip+port,客户端数据>
	public Map<String,ClientData> clientmap = new HashMap<String,ClientData>();
	
	//一个ip中有几个端口<ip,ip中端口的信息>
	public Map<String,ClientPortData> userclientmap = new HashMap<String,ClientPortData>();
	
}

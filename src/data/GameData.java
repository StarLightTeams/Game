package data;

import java.util.HashMap;
import java.util.Map;

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

	// 房间配置
	public Map<String, Room> roommap = new HashMap<String, Room>();
}

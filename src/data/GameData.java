package data;

import java.util.HashMap;
import java.util.Map;

import entity.rooms.Room;

/*
 * add by king 
 * ��Ϸ������ ����ģʽ
 */
public class GameData {

	// ����ģʽ ˫��У����
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

	// ��������
	public Map<String, Room> roommap = new HashMap<String, Room>();
}

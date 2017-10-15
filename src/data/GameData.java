package data;

import java.util.HashMap;
import java.util.Map;

import entity.client.ClientData;
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
	
	// ��������<�������ͣ������б�����ţ��������ݡ�>
	public Map<Integer, Map<String,Room>> roommap = new HashMap<Integer, Map<String,Room>>();
	
	//�ͻ����б�<����,�ͻ�������>
	public Map<Integer,ClientData> clientmap = new HashMap<Integer,ClientData>();
}

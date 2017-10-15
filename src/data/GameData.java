package data;

import java.util.HashMap;
import java.util.Map;

import entity.client.ClientData;
import entity.client.ClientPortData;
import entity.rooms.Room;

/*
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
	
	//�ͻ����б�<ip+port,�ͻ�������>
	public Map<String,ClientData> clientmap = new HashMap<String,ClientData>();
	
	//һ��ip���м����˿�<ip,ip�ж˿ڵ���Ϣ>
	public Map<String,ClientPortData> userclientmap = new HashMap<String,ClientPortData>();
	
}

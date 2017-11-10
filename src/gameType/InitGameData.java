package gameType;

import java.util.Iterator;
import java.util.Map;

import tool.RoomTools;
import config.GameConfig;
import data.GameData;
import entity.rooms.Room;
import gameType.chuachua.InitChuaChua;
import gameType.chuachua.data.ChuaChuaGameMainData;
import gameType.chuachua.entity.Game;

public class InitGameData {

	public static void initGameData(){
		
		GameData g = GameData.getSingleton();
		Map<String,Room> m =g.roommap.get(RoomTools.createRoomType(2, GameConfig.doubleCommonGame));
		for (Map.Entry<String,Room> entry : m.entrySet()) { 
			//��key  -- entry.getKey()
			//��value-- entry.getValue()
			//��ʼ��chuachua��Ϸ����Ϸ����
			Game game = InitChuaChua.initData();
			System.out.println("��ʼ����������"+entry.getKey());
			ChuaChuaGameMainData.gameData.put(entry.getKey(), game);
		}

		
		
	}
}

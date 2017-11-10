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
			//键key  -- entry.getKey()
			//键value-- entry.getValue()
			//初始化chuachua游戏的游戏数据
			Game game = InitChuaChua.initData();
			System.out.println("初始化房间数据"+entry.getKey());
			ChuaChuaGameMainData.gameData.put(entry.getKey(), game);
		}

		
		
	}
}

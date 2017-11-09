package config;

import java.util.HashMap;
import java.util.Map;

import entity.rooms.Room;

/**
 *	游戏配置 
 */
public class GameConfig {
	
	//服务器中的总人数
	public static int serverCount=0;
	//一个ip中用户的人数
	public static int clientPeopleCount = 4; 
	//二人普通房间数
	public static int doubleRoomCount=10;
	//四人普通房间数
	public static int fourRoomCount = 10;
	
	//游戏类型
	public static int doubleCommonGame = 0;
	public static int doubleSpecialGame = 1;
	public static int fourCommonGame = 2;
	public static int fourSpecialGame = 3;
	
	//游戏种类
	//[1 - "chuachua游戏"]
	public static int GAMETYPELEIXING = 1;
}

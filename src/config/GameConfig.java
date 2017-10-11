package config;

import java.util.HashMap;
import java.util.Map;

import entity.rooms.Room;

public class GameConfig {
	//端口
	public static int port = 10020;
	//房间配置
	public static Map<Integer,Room> roommap = new HashMap<Integer,Room>();
	//二人普通房间数
	public static int doubleRoomCount=10;
	//四人普通房间数
	public static int fourRoomCount = 10;
}

package config;

import java.util.HashMap;
import java.util.Map;

import entity.rooms.Room;

public class GameConfig {
	//�˿�
	public static int port = 10020;
	//��������
	public static Map<Integer,Room> roommap = new HashMap<Integer,Room>();
	//������ͨ������
	public static int doubleRoomCount=10;
	//������ͨ������
	public static int fourRoomCount = 10;
}

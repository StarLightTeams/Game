package config;

import java.util.HashMap;
import java.util.Map;

import entity.rooms.Room;

/**
 *	��Ϸ���� 
 */
public class GameConfig {
	
	//�������е�������
	public static int serverCount=0;
	//һ��ip���û�������
	public static int clientPeopleCount = 4; 
	//������ͨ������
	public static int doubleRoomCount=10;
	//������ͨ������
	public static int fourRoomCount = 10;
	
	//��Ϸ����
	public static int doubleCommonGame = 0;
	public static int doubleSpecialGame = 1;
	public static int fourCommonGame = 2;
	public static int fourSpecialGame = 3;
	
	//��Ϸ����
	//[1 - "chuachua��Ϸ"]
	public static int GAMETYPELEIXING = 1;
}

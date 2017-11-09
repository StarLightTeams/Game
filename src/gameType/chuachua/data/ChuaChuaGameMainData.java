package gameType.chuachua.data;

import gameType.chuachua.entity.Ball;
import gameType.chuachua.entity.Board;
import gameType.chuachua.entity.BoardProps;
import gameType.chuachua.entity.Brick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChuaChuaGameMainData {
	//����б�
	public static List<Ball> ball_list =new ArrayList<Ball>();
	//�Լ�ש���б�
	public static List<Brick> myBrickList =new ArrayList<Brick>();
	//�з�ש���б�
	public static List<Brick> enemyBrickList =new ArrayList<Brick>();
	//�Լ��İ�
	Board myborad =new Board();
	//���˵İ�
	Board enemyborad =new Board();
	//�з��ĵ����б�
	public static Map<Integer,BoardProps> boardPropsmap =new HashMap<Integer,BoardProps>();
}

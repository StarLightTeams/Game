package gameType.chuachua.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
	//����б�
	public  List<Ball> ball_list =new ArrayList<Ball>();
	//�Լ�ש���б�
	public  List<Brick> myBrickList =new ArrayList<Brick>();
	//�з�ש���б�
	public  List<Brick> enemyBrickList =new ArrayList<Brick>();
	//�Լ��İ�
	public  Board myborad =new Board();
	//���˵İ�
	public  Board enemyborad =new Board();
	//�з��ĵ����б�
	public  Map<Integer,BoardProps> boardPropsmap =new HashMap<Integer,BoardProps>();
}

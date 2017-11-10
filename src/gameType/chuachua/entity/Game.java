package gameType.chuachua.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
	//球的列表
	public  List<Ball> ball_list =new ArrayList<Ball>();
	//自己砖的列表
	public  List<Brick> myBrickList =new ArrayList<Brick>();
	//敌方砖的列表
	public  List<Brick> enemyBrickList =new ArrayList<Brick>();
	//自己的板
	public  Board myborad =new Board();
	//敌人的板
	public  Board enemyborad =new Board();
	//敌方的道具列表
	public  Map<Integer,BoardProps> boardPropsmap =new HashMap<Integer,BoardProps>();
}

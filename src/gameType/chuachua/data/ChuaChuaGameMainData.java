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
	//球的列表
	public static List<Ball> ball_list =new ArrayList<Ball>();
	//自己砖的列表
	public static List<Brick> myBrickList =new ArrayList<Brick>();
	//敌方砖的列表
	public static List<Brick> enemyBrickList =new ArrayList<Brick>();
	//自己的板
	Board myborad =new Board();
	//敌人的板
	Board enemyborad =new Board();
	//敌方的道具列表
	public static Map<Integer,BoardProps> boardPropsmap =new HashMap<Integer,BoardProps>();
}

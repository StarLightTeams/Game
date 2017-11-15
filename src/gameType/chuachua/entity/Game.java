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
	public List<Ball> getBall_list() {
		return ball_list;
	}
	public void setBall_list(List<Ball> ball_list) {
		this.ball_list = ball_list;
	}
	public List<Brick> getMyBrickList() {
		return myBrickList;
	}
	public void setMyBrickList(List<Brick> myBrickList) {
		this.myBrickList = myBrickList;
	}
	public List<Brick> getEnemyBrickList() {
		return enemyBrickList;
	}
	public void setEnemyBrickList(List<Brick> enemyBrickList) {
		this.enemyBrickList = enemyBrickList;
	}
	public Board getMyborad() {
		return myborad;
	}
	public void setMyborad(Board myborad) {
		this.myborad = myborad;
	}
	public Board getEnemyborad() {
		return enemyborad;
	}
	public void setEnemyborad(Board enemyborad) {
		this.enemyborad = enemyborad;
	}
	public Map<Integer, BoardProps> getBoardPropsmap() {
		return boardPropsmap;
	}
	public void setBoardPropsmap(Map<Integer, BoardProps> boardPropsmap) {
		this.boardPropsmap = boardPropsmap;
	}
	
	
}

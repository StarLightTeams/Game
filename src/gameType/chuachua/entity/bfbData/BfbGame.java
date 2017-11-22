package gameType.chuachua.entity.bfbData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameType.chuachua.entity.BoardProps;

public class BfbGame {
	//球的列表
	public  List<BfbBall> ball_list =new ArrayList<BfbBall>();
	//自己砖的列表
	public  List<BfbBrick> myBrickList =new ArrayList<BfbBrick>();
	//敌方砖的列表
	public  List<BfbBrick> enemyBrickList =new ArrayList<BfbBrick>();
	//自己的板
	public  BfbBoard myborad =new BfbBoard();
	//敌人的板
	public  BfbBoard enemyborad =new BfbBoard();
	//敌方的道具列表
	public  Map<Integer,BoardProps> boardPropsmap =new HashMap<Integer,BoardProps>();
	public List<BfbBall> getBall_list() {
		return ball_list;
	}
	public void setBall_list(List<BfbBall> ball_list) {
		this.ball_list = ball_list;
	}
	public List<BfbBrick> getMyBrickList() {
		return myBrickList;
	}
	public void setMyBrickList(List<BfbBrick> myBrickList) {
		this.myBrickList = myBrickList;
	}
	public List<BfbBrick> getEnemyBrickList() {
		return enemyBrickList;
	}
	public void setEnemyBrickList(List<BfbBrick> enemyBrickList) {
		this.enemyBrickList = enemyBrickList;
	}
	public BfbBoard getMyborad() {
		return myborad;
	}
	public void setMyborad(BfbBoard myborad) {
		this.myborad = myborad;
	}
	public BfbBoard getEnemyborad() {
		return enemyborad;
	}
	public void setEnemyborad(BfbBoard enemyborad) {
		this.enemyborad = enemyborad;
	}
	public Map<Integer, BoardProps> getBoardPropsmap() {
		return boardPropsmap;
	}
	public void setBoardPropsmap(Map<Integer, BoardProps> boardPropsmap) {
		this.boardPropsmap = boardPropsmap;
	}
	@Override
	public String toString() {
		return "Game [ball_list=" + ball_list + ", myBrickList=" + myBrickList + ", enemyBrickList=" + enemyBrickList
				+ ", myborad=" + myborad + ", enemyborad=" + enemyborad + ", boardPropsmap=" + boardPropsmap + "]";
	}
	
}

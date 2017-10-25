package entity.player;

import java.util.Map;

/*
 * 玩家类
 */
public class Player {
	//玩家序号
	public int playerNo;
	//玩家名字
	public String playerName;
	//玩家登陆密码
	public String password;
	//玩家房卡
	public int playerCard;
	//玩家道具
	public Map<String,Integer> djmap;
	//玩家状态【】
	
	
	public Player() {
		
	}
	
	public Player(String playerName ,String password) {
		this.playerName = playerName;
		this.password = password;
	}
	
	
	public int getPlayerNo() {
		return playerNo;
	}
	public void setPlayerNo(int playerNo) {
		this.playerNo = playerNo;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getPlayerCard() {
		return playerCard;
	}
	public void setPlayerCard(int playerCard) {
		this.playerCard = playerCard;
	}
	public Map<String, Integer> getDjmap() {
		return djmap;
	}
	public void setDjmap(Map<String, Integer> djmap) {
		this.djmap = djmap;
	}
	@Override
	public String toString() {
		return "Player [playerNo=" + playerNo + ", playerName=" + playerName + ", password=" + password
				+ ", playerCard=" + playerCard + ", djmap=" + djmap + "]";
	}
	
}


package entity.player;

import java.util.HashMap;
import java.util.Map;

/*
 * 玩家类
 */
public class Player {
	//玩家的ip+:+port
	public String clientId;
	//玩家序号
	public int playerNo;
	//玩家名字
	public String playerName;
	//玩家登陆密码(游客密码默认为1)
	public String password = "1";
	//玩家房卡
	public int playerCard;
	//玩家道具
	public Map<String,Integer> djmap = new HashMap<String, Integer>();
	//玩家登录状态【 0.游客登录1.qq登录 2.微信登录 4登录】
	public int loginState = 0;
	//游戏状态[0未进行游戏 1.等待游戏2.正在游戏3.结束游戏]
	public int gamestate =0;
	
	public Player() {
		
	}
	
	public Player(String playerName) {
		this.playerName = playerName;
	}
	
	public Player(String playerName ,String password,String clientId) {
		this.playerName = playerName;
		this.password = password;
		this.clientId = clientId;
	}
	
	public Player(String playerName,String password,int loginState,String clientId) {
		this.playerName = playerName;
		this.password = password;
		this.loginState =loginState;
		this.clientId = clientId;
	}
	
	
	
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getGamestate() {
		return gamestate;
	}

	public void setGamestate(int gamestate) {
		this.gamestate = gamestate;
	}

	public int getLoginState() {
		return loginState;
	}

	public void setLoginState(int loginState) {
		this.loginState = loginState;
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
		return "Player [clientId=" + clientId + ", playerNo=" + playerNo + ", playerName=" + playerName + ", password="
				+ password + ", playerCard=" + playerCard + ", djmap=" + djmap + ", loginState=" + loginState
				+ ", gamestate=" + gamestate + "]";
	}
}


package entity.player;

import java.util.HashMap;
import java.util.Map;

/*
 * �����
 */
public class Player {
	//��ҵ�ip+:+port
	public String clientId;
	//������
	public int playerNo;
	//�������
	public String playerName;
	//��ҵ�½����(�ο�����Ĭ��Ϊ1)
	public String password = "1";
	//��ҷ���
	public int playerCard;
	//��ҵ���
	public Map<String,Integer> djmap = new HashMap<String, Integer>();
	//��ҵ�¼״̬�� 0.�ο͵�¼1.qq��¼ 2.΢�ŵ�¼ 4��¼��
	public int loginState = 0;
	//��Ϸ״̬[0δ������Ϸ 1.�ȴ���Ϸ2.������Ϸ3.������Ϸ]
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


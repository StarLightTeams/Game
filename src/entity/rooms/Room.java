package entity.rooms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import entity.player.Player;
import rule.RoomRule;

/**
 * ������
 */
public class Room {
	//������Ϣ
	public RoomInfo roomInfo =new RoomInfo(); 
	//����������Ϣ
	public Map<Player,Integer> playermap = new HashMap<Player,Integer>();
	//�������
	public RoomRule roomRule = new RoomRule();
	public RoomInfo getRoomInfo() {
		return roomInfo;
	}
	public void setRoomInfo(RoomInfo roomInfo) {
		this.roomInfo = roomInfo;
	}
	public Map<Player, Integer> getPlayermap() {
		return playermap;
	}
	public void setPlayermap(Map<Player, Integer> playermap) {
		this.playermap = playermap;
	}
	public RoomRule getRoomRule() {
		return roomRule;
	}
	public void setRoomRule(RoomRule roomRule) {
		this.roomRule = roomRule;
	}
	
}

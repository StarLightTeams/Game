package entity.rooms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import entity.player.Player;
import rule.RoomRule;

/**
 * 房间类
 */
public class Room {
	//房间信息
	public RoomInfo roomInfo =new RoomInfo(); 
	//房间的玩家信息
	public Map<Player,Integer> playermap = new HashMap<Player,Integer>();
	//房间规则
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

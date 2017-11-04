package entity.rooms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import entity.player.Player;
import rule.RoomRule;

/**
 * 房间类
 */
public abstract class Room {
	//房间信息
	public RoomInfo roomInfo =new RoomInfo(); 
	//房间的玩家信息
	public Map<Player,Integer> playermap = new HashMap<Player,Integer>();
	//房间规则
	public RoomRule roomRule = new RoomRule();

}

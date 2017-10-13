package entity.rooms;

import java.util.Date;
import java.util.Map;
import entity.player.Player;
import rule.RoomRule;

/**
 * 房间类
 */
public abstract class Room {
	//房间信息
	public RoomInfo roomInfo; 
	//房间的玩家信息
	public Map<Player,Integer> playermap;
	//房间规则
	public RoomRule roomRule;
}

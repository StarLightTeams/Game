package entity.rooms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import entity.player.Player;
import rule.RoomRule;

/**
 * ������
 */
public abstract class Room {
	//������Ϣ
	public RoomInfo roomInfo =new RoomInfo(); 
	//����������Ϣ
	public Map<Player,Integer> playermap = new HashMap<Player,Integer>();
	//�������
	public RoomRule roomRule = new RoomRule();

}

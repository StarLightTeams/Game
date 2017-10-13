package entity.rooms;

import java.util.Date;
import java.util.Map;
import entity.player.Player;
import rule.RoomRule;

/**
 * ������
 */
public abstract class Room {
	//������Ϣ
	public RoomInfo roomInfo; 
	//����������Ϣ
	public Map<Player,Integer> playermap;
	//�������
	public RoomRule roomRule;
}

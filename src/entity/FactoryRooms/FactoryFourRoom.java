package entity.FactoryRooms;

import entity.rooms.FourRoom;
import inters.FactoryRoom;

/**
 * ������ͨ�乤��
 * @author zb
 *
 */

public class FactoryFourRoom implements FactoryRoom{
	public FourRoom createRoom(String roomId) {
		return new FourRoom(roomId);
	}
}

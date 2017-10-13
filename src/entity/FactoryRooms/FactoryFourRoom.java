package entity.FactoryRooms;

import entity.rooms.FourRoom;
import inters.FactoryRoom;

/**
 * 四人普通间工厂
 * @author zb
 *
 */

public class FactoryFourRoom implements FactoryRoom{
	public FourRoom createRoom(String roomId) {
		return new FourRoom(roomId);
	}
}

package entity.FactoryRooms;

import entity.rooms.DoubleRoom;
import entity.rooms.Room;
import inters.FactoryRoom;

/**
 * 双人普通间工厂
 * @author zb
 *
 */

public class FactoryDoubleRoom implements FactoryRoom {

	public DoubleRoom createRoom(String roomId) {
		return new DoubleRoom(roomId);
	}
}
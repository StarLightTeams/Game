package entity.rooms;

import java.util.Date;

/**
 * ˫����ͨ��
 */
public class DoubleRoom extends Room {
	
	public DoubleRoom(String roomId){
		super.roomInfo.roomId = roomId;
		super.roomInfo.roomType ="double";
		super.roomInfo.createRoomTime = new Date();
	}
}

package entity.rooms;

import java.util.Date;

/**
 * Ë«ÈËÆÕÍ¨¼ä
 * @author zb
 *
 */

public class DoubleRoom extends Room {
	
	
	public DoubleRoom(String roomId){
		super.roomInfo.roomId = roomId;
		super.roomInfo.roomType ="double";
		super.roomInfo.createRoomTime = new Date();
	}
}

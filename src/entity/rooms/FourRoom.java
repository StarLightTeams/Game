package entity.rooms;

import java.util.Date;

/**
 * ������ͨ��
 */
public class FourRoom extends Room{
	
	public FourRoom(String roomId){
		super.roomInfo.roomId = roomId;
		super.roomInfo.roomType ="four";
		super.roomInfo.createRoomTime = new Date();
	}
}

package entity.rooms;

import java.util.Date;

import config.GameConfig;
import tool.RoomTools;

/**
 * Ë«ÈËÆÕÍ¨¼ä
 */
public class DoubleRoom extends Room {
	
	public DoubleRoom(String roomId){
		super.roomInfo.roomId = roomId;
		super.roomInfo.roomType =RoomTools.createRoomType(2, GameConfig.doubleCommonGame);
		super.roomInfo.createRoomTime = new Date();
	}
	
}

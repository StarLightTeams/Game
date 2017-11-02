package entity.rooms;

import java.util.Date;

import config.GameConfig;
import tool.RoomTools;

/**
 * 四人普通间
 */
public class FourRoom extends Room{
	
	public FourRoom(String roomId){
		super.roomInfo.roomId = roomId;
		super.roomInfo.roomType =RoomTools.createRoomType(4, GameConfig.fourCommonGame);
		super.roomInfo.createRoomTime = new Date();
	}
}

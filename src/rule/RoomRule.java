package rule;

import java.util.Date;

/**
 *	房间规则类 
 */
public class RoomRule {
	//房间规则【1- ,2- ,3- 】
	public int RoomXXRule;
	//房间消耗房卡数量
	public int RoomCostCardCount;
	public int getRoomXXRule() {
		return RoomXXRule;
	}
	public void setRoomXXRule(int roomXXRule) {
		RoomXXRule = roomXXRule;
	}
	public int getRoomCostCardCount() {
		return RoomCostCardCount;
	}
	public void setRoomCostCardCount(int roomCostCardCount) {
		RoomCostCardCount = roomCostCardCount;
	}
	
}

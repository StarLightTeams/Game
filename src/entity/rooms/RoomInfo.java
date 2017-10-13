package entity.rooms;

import java.util.Date;

public class RoomInfo {
	//房间号 
	public String roomId;
	//房间创建时间
	public Date createRoomTime;
	//房间玩法
	public String roomType;
	//房间人数
	public int RoomPeopleCount;
	//房间状态位【1.等人中。2.开始中。3.关闭中。】
	public int RoomState;
	//房间最后1局结束时间
	public Date RoomCreateTime;
}

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
	public int RoomState =1 ;
	//房间最后1局结束时间
	public Date RoomCreateTime;
	//房间的优先级(与房间人数挂钩,最高是(房间总人数-1),房间人满优先级为-1)
	public int roomPLevel = 0;
	//游戏加载完人数(等于房间人数正式开始游戏)
	public int endOfLoadingGame = 0;
}

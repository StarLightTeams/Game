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
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public Date getCreateRoomTime() {
		return createRoomTime;
	}
	public void setCreateRoomTime(Date createRoomTime) {
		this.createRoomTime = createRoomTime;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public int getRoomPeopleCount() {
		return RoomPeopleCount;
	}
	public void setRoomPeopleCount(int roomPeopleCount) {
		RoomPeopleCount = roomPeopleCount;
	}
	public int getRoomState() {
		return RoomState;
	}
	public void setRoomState(int roomState) {
		RoomState = roomState;
	}
	public Date getRoomCreateTime() {
		return RoomCreateTime;
	}
	public void setRoomCreateTime(Date roomCreateTime) {
		RoomCreateTime = roomCreateTime;
	}
	public int getRoomPLevel() {
		return roomPLevel;
	}
	public void setRoomPLevel(int roomPLevel) {
		this.roomPLevel = roomPLevel;
	}
	public int getEndOfLoadingGame() {
		return endOfLoadingGame;
	}
	public void setEndOfLoadingGame(int endOfLoadingGame) {
		this.endOfLoadingGame = endOfLoadingGame;
	}
	
}

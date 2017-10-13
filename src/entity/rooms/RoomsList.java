package entity.rooms;

import java.util.ArrayList;

public class RoomsList {
	
	public ArrayList<DoubleRoom> doubleRooms;
	public ArrayList<FourRoom> fourRooms;
	
	//列表中的房间数(初始化固定)
	private int doubleRoomCount;
	private int fourRoomCount;
	
	public RoomsList(int doubleRoomCount,int fourRoomCount) {
		doubleRooms = new ArrayList<DoubleRoom>();
		fourRooms = new ArrayList<FourRoom>();
		this.doubleRoomCount = doubleRoomCount;
		this.fourRoomCount = fourRoomCount;
	}

	public int getDoubleRoomCount() {
		return doubleRoomCount;
	}
	public void setDoubleRoomCount(int doubleRoomCount) {
		this.doubleRoomCount = doubleRoomCount;
	}
	public int getFourRoomCount() {
		return fourRoomCount;
	}
	public void setFourRoomCount(int fourRoomCount) {
		this.fourRoomCount = fourRoomCount;
	}
	
	
	
}

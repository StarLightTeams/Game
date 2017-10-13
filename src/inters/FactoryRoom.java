package inters;

import entity.rooms.Room;

public interface FactoryRoom {
	Room createRoom(String roomId);
}

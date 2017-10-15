package inters;

import entity.rooms.Room;

/**
 * 工厂房间类
 */
public interface FactoryRoom {
	Room createRoom(String roomId);
}

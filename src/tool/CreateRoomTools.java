package tool;

import java.util.HashMap;
import java.util.Map;

import entity.rooms.Room;

/*
 * 创建房间的工具类，所有的创建房间所需的工具
 */
public class CreateRoomTools {

	//创建房间id
	public static String createRoomID(int peoplecount/*创建房间的玩家最大人数*/,int no/*房间序列号*/){
		String roomid="";
		if(no<10){
			roomid = peoplecount+"00"+no;
		}else if(no>10&&no<100){
			roomid = peoplecount+"0"+no;
		}else{
			roomid = peoplecount+""+no;
		}
		return roomid;
	}
	//插入表数据
	public static boolean insertTable(Map<Integer, Map<String,Room>> roommap/*房间列表数据*/,int roomlx/*类型*/,String roomno/*房间序号*/,Room room/*房间数据*/){
		Map<String,Room> t =new HashMap<String,Room>();
		//房间
		if(roommap.get(roomlx).get(roomno)!=null){
			System.out.println("创建房间"+roomno+"失败");
			return false;
		}else{
			roommap.get(roomlx).put(roomno, room);
		}
		return true;
	}
}

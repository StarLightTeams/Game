package tool;
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
}

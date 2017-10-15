package tool;

import java.util.HashMap;
import java.util.Map;

import entity.rooms.Room;

/*
 * ��������Ĺ����࣬���еĴ�����������Ĺ���
 */
public class CreateRoomTools {

	//��������id
	public static String createRoomID(int peoplecount/*�������������������*/,int no/*�������к�*/){
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
	//���������
	public static boolean insertTable(Map<Integer, Map<String,Room>> roommap/*�����б�����*/,int roomlx/*����*/,String roomno/*�������*/,Room room/*��������*/){
		Map<String,Room> t =new HashMap<String,Room>();
		//����
		if(roommap.get(roomlx).get(roomno)!=null){
			System.out.println("��������"+roomno+"ʧ��");
			return false;
		}else{
			roommap.get(roomlx).put(roomno, room);
		}
		return true;
	}
}

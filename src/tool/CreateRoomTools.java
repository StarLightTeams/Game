package tool;
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
}

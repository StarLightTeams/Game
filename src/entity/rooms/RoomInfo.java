package entity.rooms;

import java.util.Date;

public class RoomInfo {
	//����� 
	public String roomId;
	//���䴴��ʱ��
	public Date createRoomTime;
	//�����淨
	public String roomType;
	//��������
	public int RoomPeopleCount;
	//����״̬λ��1.�����С�2.��ʼ�С�3.�ر��С���
	public int RoomState =1 ;
	//�������1�ֽ���ʱ��
	public Date RoomCreateTime;
	//��������ȼ�(�뷿�������ҹ�,�����(����������-1),�����������ȼ�Ϊ-1)
	public int roomPLevel = 0;
	//��Ϸ����������(���ڷ���������ʽ��ʼ��Ϸ)
	public int endOfLoadingGame = 0;
}

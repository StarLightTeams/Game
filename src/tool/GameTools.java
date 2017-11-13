package tool;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import config.ClientConfig;
import config.GameConfig;
import config.RoomConfig;
import config.entity.Log;
import data.GameData;
import entity.FactoryRooms.FactoryDoubleRoom;
import entity.FactoryRooms.FactoryFourRoom;
import entity.IO.MainIO;
import entity.agrement.ICommand;
import entity.client.ClientData;
import entity.info.Info;
import entity.player.Player;
import entity.rooms.DoubleRoom;
import entity.rooms.FourRoom;
import entity.rooms.Room;
import gameType.chuachua.data.ChuaChuaGameMainData;
import main.TimeServerHandlerExecute;
import rule.agreement.GamePreparingErrorCommand;
import rule.agreement.GeneralInformationCommand;
import rule.agreement.VerifyStateCommand;
import rule.agreement.VerifyStateErrCommand;
import rule.agreement.WaitOtherPeopleCommand;

public class GameTools {
	
	GameData gameData;
	
	public GameTools() {
		
	}
	
	public GameTools(GameData gameData) {
		this.gameData = gameData;
	}
	
	/**
	 * ��Ϸ׼��
	 * @param dataInfo
	 * @param mainIo
	 */
	public void gamePreparing(String dataInfo,MainIO mainIo,TimeServerHandlerExecute singleExecutor) {
		Map<String,String> maps = JsonTools.parseData(dataInfo);
		String gameType = maps.get("gameType");
		String clientId = maps.get("clientId");
		String roomKey = maps.get("roomKey");
		Log.d("gameType="+gameType+",clientId="+clientId+",roomKey="+roomKey);
		//ȡ����Ӧ�Ŀͻ���
		ClientData client = gameData.clientmap.get(clientId);
		Player player = client.getPlayer();
		Room room = null;
		//�жϿͻ����Ƿ��¼,�Ƿ��ڴ���,���״̬�Ƿ�����Ϸ(���Ż�)
		if(client.isIslogin()) {
			if(client.getClientLocState()==ClientConfig.LOGININHALL) {
				if(player.getGamestate()==ClientConfig.noGame) {
					Map<String,Room> rooms = gameData.roommap.get(gameType);
					System.out.println(rooms.isEmpty());
					//�ж��Ƿ񷿼��Ѿ�������
					if(!judgeRoomsFull(rooms)) {
						//�ҵ����ȼ��ϸߵķ���
						room = findPLvhRoom(rooms);
						//���λ��
						int table = room.roomInfo.RoomPeopleCount+1;
						//�������
						room.playermap.put(player, table);
						//�ı䷿�����ȼ������·���״̬�����¿ͻ��˵�״̬,�������״̬
						room.roomInfo.RoomPeopleCount++;
						room.roomInfo.roomPLevel = room.roomInfo.RoomPeopleCount==getRoomPlayerNum(room)?-1:room.roomInfo.RoomPeopleCount;
						client.setClientLocState(ClientConfig.ROOMPREPARING);
						player.gamestate = ClientConfig.waitGame;
						mainIo.sendMessage(new GeneralInformationCommand(),JsonTools.getString(new Info("���뷿��"+room.roomInfo.roomId)));
						//�жϴ˷����Ƿ�����
						if(judgeRoomFull(room)) {
							//��֤�������״̬,�������Ӧ�Ŀͻ���״̬,��ҵ�½״̬ ����
							Map<Player,Integer> players = room.playermap;
							Info info = judgeState(players,room);
							if(info.headInfo.equals("��֤�ɹ�")) {
								//��������������ҷ�����֤�ɹ�
								DataTransmitTools.sendAllClientsMessage(players,new VerifyStateCommand(),JsonTools.getString(info),singleExecutor);
							}else if(info.headInfo.equals("��֤ʧ��")) {
								mainIo.sendMessage(new VerifyStateErrCommand(), JsonTools.getString(info));
							}
						}else {
							mainIo.sendMessage(new WaitOtherPeopleCommand() ,JsonTools.getString(new Info("����ƥ����...",JsonTools.getString(room))));
						}
					}else {
						//����(���Ż�,����)
						mainIo.sendMessage(new GamePreparingErrorCommand(), JsonTools.getString(new Info("��Ϸ׼������",gameType+"���͵����з�������")));
					}
				}else {
					mainIo.sendMessage(new GamePreparingErrorCommand(), JsonTools.getString(new Info("��Ϸ׼������","�������Ϸ")));
				}
			}else {
				mainIo.sendMessage(new GamePreparingErrorCommand(), JsonTools.getString(new Info("��Ϸ׼������","�ͻ��˲��ڴ���")));
			}
		}else {
			mainIo.sendMessage(new GamePreparingErrorCommand(), JsonTools.getString(new Info("��Ϸ׼������","�ͻ���δ��¼")));
		}
	}
	
	
	/**
	 * �ж���ҵ�״̬
	 * @param players
	 * @return
	 */
	public Info judgeState(Map<Player,Integer> players,Room room) {
		Iterator entries = players.entrySet().iterator(); 
		Info info = new Info();
		Map<String,String> maps = new HashMap<String, String>();
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();  
			Player player = (Player) entry.getKey();  
			String clientID = player.getClientId();
			ClientData client = gameData.clientmap.get(clientID);
			boolean flag = false;
			String dataInfo = "";
			//��Ҫ�Ż�,islive
			if(client.getClientLocState() != ClientConfig.ROOMPREPARING ) {
				dataInfo += "�ͻ��˵�״̬����";
				flag = true;
			}else {
				if(client.isIslogin() == false) {
					dataInfo+="�ͻ���δ��¼";
					flag = true;
				}else {
					if(player.gamestate != ClientConfig.waitGame) {
						dataInfo += "���״̬���ǵȴ���Ϸ";
						flag = true;
					}
				}
			}
			if(flag) {
				maps.put(clientID,dataInfo);
			}
		}
		String headInfo;
		if(maps.isEmpty()) {
			headInfo = "��֤�ɹ�";
			maps.put("roomId", room.roomInfo.roomId);
			maps.put("roomType", room.roomInfo.roomType);
			//����Game��
			maps.put("Game", JsonTools.getString( ChuaChuaGameMainData.gameData.get(room.roomInfo.roomId)));
		}else {
			headInfo = "��֤ʧ��";
		}
		return new Info(headInfo,JsonTools.getData(maps));
	}
	
	/**	
	 * �жϷ����Ƿ�����
	 * @param room
	 * @return
	 */
	public boolean judgeRoomFull(Room room ) {
		int lv = room.roomInfo.roomPLevel;
		int pNum = room.roomInfo.RoomPeopleCount;
		if(lv==-1 && pNum==getRoomPlayerNum(room)) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * ���ش����ͷ��俪ʼ��Ϸ����
	 * @param type
	 * @return
	 */
	public int getRoomPlayerNum(Room room) {
		String type = room.roomInfo.roomType;
		return RoomTools.getRoomPeopleNumByRoomType(type);
	}

	/**
	 * �ҵ��ϸߵ����ȼ�����
	 * @param rooms
	 * @return
	 */
	public Room findPLvhRoom(Map<String,Room> rooms) {
		Iterator entries = rooms.entrySet().iterator();  
		int maxPLv = -1;
		Room HRoom = null ;
		while (entries.hasNext()) {  
		    Map.Entry entry = (Map.Entry) entries.next();  
		    Room room = (Room) entry.getValue();  
		    int roomPL = room.roomInfo.roomPLevel;
		    if(roomPL!=-1 && roomPL>maxPLv) {
		    	maxPLv = roomPL;
		    	HRoom = room;
		    }
		}  
		return HRoom;
	}
	
	/**
	 * �ж�һ�����͵ķ���Lie���Ƿ��Ѿ�����
	 * ���˷���:ture,����false
	 * @param rooms
	 * @return
	 */
	public boolean judgeRoomsFull(Map<String,Room> rooms) {
		boolean flag = false;
		for(Map.Entry<String, Room> entry:rooms.entrySet()) {
			Room room = entry.getValue();
			//����״̬��Ϊ����״̬ ���� �������ȼ���Ϊ-1
			if(room.roomInfo.RoomState != RoomConfig.waiting || room.roomInfo.roomPLevel!=-1 ) {
				flag= true;
				break;
			}
		}
		return !flag;
	}
	
	@Test
	public void test() {
//		GameData g = GameData.getSingleton();
//		g.roommap.put(2, new HashMap<String, Room>());
//		g.roommap.put(4, new HashMap<String, Room>());
//		// ˫����ͨ��
//		for (int i = 0; i < GameConfig.doubleRoomCount; i++) {
//			String roomId = RoomTools.createRoomID(2, i);
//			DoubleRoom dr = new FactoryDoubleRoom().createRoom(roomId);
//			RoomTools.insertTable(g.roommap, 2, roomId, dr);
//		}
//		// ������ͨ��
//		for (int i = 0; i < GameConfig.fourRoomCount; i++) {
//			String roomId = RoomTools.createRoomID(4, i);
//			FourRoom fr = new FactoryFourRoom().createRoom(roomId);
//			RoomTools.insertTable(g.roommap, 4, roomId, fr);
//		}
//		GameTools gg = new GameTools();
//		Map<String,Room> rooms = g.roommap.get(2);
//		System.out.println(gg.judgeRoomsFull(rooms));
		List<Player> players = new ArrayList<Player>();
		for(int i=0;i<10;i++) {
			Player p = new Player(i+""); 
			players.add(p);
		}
		Player player = players.get(3);
		player.setPlayerName("faker");
		for(int i=0;i<10;i++) {
			System.out.println(i+","+players.get(i).getPlayerName());
		}
	}
	
}

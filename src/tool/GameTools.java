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
	 * 游戏准备
	 * @param dataInfo
	 * @param mainIo
	 */
	public void gamePreparing(String dataInfo,MainIO mainIo,TimeServerHandlerExecute singleExecutor) {
		Map<String,String> maps = JsonTools.parseData(dataInfo);
		String gameType = maps.get("gameType");
		String clientId = maps.get("clientId");
		String roomKey = maps.get("roomKey");
		Log.d("gameType="+gameType+",clientId="+clientId+",roomKey="+roomKey);
		//取出对应的客户端
		ClientData client = gameData.clientmap.get(clientId);
		Player player = client.getPlayer();
		Room room = null;
		//判断客户端是否登录,是否在大厅,玩家状态是否不在游戏(待优化)
		if(client.isIslogin()) {
			if(client.getClientLocState()==ClientConfig.LOGININHALL) {
				if(player.getGamestate()==ClientConfig.noGame) {
					Map<String,Room> rooms = gameData.roommap.get(gameType);
					System.out.println(rooms.isEmpty());
					//判断是否房间已经满人了
					if(!judgeRoomsFull(rooms)) {
						//找到优先级较高的房间
						room = findPLvhRoom(rooms);
						//玩家位置
						int table = room.roomInfo.RoomPeopleCount+1;
						//放入玩家
						room.playermap.put(player, table);
						//改变房间优先级，更新房间状态，更新客户端的状态,更新玩家状态
						room.roomInfo.RoomPeopleCount++;
						room.roomInfo.roomPLevel = room.roomInfo.RoomPeopleCount==getRoomPlayerNum(room)?-1:room.roomInfo.RoomPeopleCount;
						client.setClientLocState(ClientConfig.ROOMPREPARING);
						player.gamestate = ClientConfig.waitGame;
						mainIo.sendMessage(new GeneralInformationCommand(),JsonTools.getString(new Info("加入房间"+room.roomInfo.roomId)));
						//判断此房间是否满人
						if(judgeRoomFull(room)) {
							//验证所有玩家状态,玩家所对应的客户端状态,玩家登陆状态 符合
							Map<Player,Integer> players = room.playermap;
							Info info = judgeState(players,room);
							if(info.headInfo.equals("验证成功")) {
								//给房间里所有玩家发送验证成功
								DataTransmitTools.sendAllClientsMessage(players,new VerifyStateCommand(),JsonTools.getString(info),singleExecutor);
							}else if(info.headInfo.equals("验证失败")) {
								mainIo.sendMessage(new VerifyStateErrCommand(), JsonTools.getString(info));
							}
						}else {
							mainIo.sendMessage(new WaitOtherPeopleCommand() ,JsonTools.getString(new Info("正在匹配人...",JsonTools.getString(room))));
						}
					}else {
						//满了(待优化,房卡)
						mainIo.sendMessage(new GamePreparingErrorCommand(), JsonTools.getString(new Info("游戏准备出错",gameType+"类型的所有房间已满")));
					}
				}else {
					mainIo.sendMessage(new GamePreparingErrorCommand(), JsonTools.getString(new Info("游戏准备出错","玩家在游戏")));
				}
			}else {
				mainIo.sendMessage(new GamePreparingErrorCommand(), JsonTools.getString(new Info("游戏准备出错","客户端不在大厅")));
			}
		}else {
			mainIo.sendMessage(new GamePreparingErrorCommand(), JsonTools.getString(new Info("游戏准备出错","客户端未登录")));
		}
	}
	
	
	/**
	 * 判断玩家的状态
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
			//需要优化,islive
			if(client.getClientLocState() != ClientConfig.ROOMPREPARING ) {
				dataInfo += "客户端的状态不对";
				flag = true;
			}else {
				if(client.isIslogin() == false) {
					dataInfo+="客户端未登录";
					flag = true;
				}else {
					if(player.gamestate != ClientConfig.waitGame) {
						dataInfo += "玩家状态不是等待游戏";
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
			headInfo = "验证成功";
			maps.put("roomId", room.roomInfo.roomId);
			maps.put("roomType", room.roomInfo.roomType);
			//加入Game类
			maps.put("Game", JsonTools.getString( ChuaChuaGameMainData.gameData.get(room.roomInfo.roomId)));
		}else {
			headInfo = "验证失败";
		}
		return new Info(headInfo,JsonTools.getData(maps));
	}
	
	/**	
	 * 判断房间是否已满
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
	 * 返回此类型房间开始游戏人数
	 * @param type
	 * @return
	 */
	public int getRoomPlayerNum(Room room) {
		String type = room.roomInfo.roomType;
		return RoomTools.getRoomPeopleNumByRoomType(type);
	}

	/**
	 * 找到较高的优先级房间
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
	 * 判断一种类型的房间Lie表是否已经满人
	 * 满人返回:ture,否则false
	 * @param rooms
	 * @return
	 */
	public boolean judgeRoomsFull(Map<String,Room> rooms) {
		boolean flag = false;
		for(Map.Entry<String, Room> entry:rooms.entrySet()) {
			Room room = entry.getValue();
			//房间状态不为等人状态 或者 房间优先级不为-1
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
//		// 双人普通房
//		for (int i = 0; i < GameConfig.doubleRoomCount; i++) {
//			String roomId = RoomTools.createRoomID(2, i);
//			DoubleRoom dr = new FactoryDoubleRoom().createRoom(roomId);
//			RoomTools.insertTable(g.roommap, 2, roomId, dr);
//		}
//		// 四人普通房
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

package tool;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import data.GameData;
import entity.IO.MainIO;
import entity.agrement.ICommand;
import entity.client.ClientData;
import entity.info.Info;
import entity.player.Player;
import entity.rooms.Room;
import gameType.chuachua.data.ChuaChuaGameMainData;
import gameType.chuachua.entity.Board;
import gameType.chuachua.entity.Game;
import main.TimeServerHandlerExecute;

/**
 * 全局传输数据类
 */
public class DataTransmitTools {
	
	/**
	 * 给玩家传输数据
	 * @param players
	 * @param icommand
	 * @param str
	 * @param singleExecutor
	 */
	public synchronized static void sendClientsMessage(List<Player> players,ICommand icommand,String str,TimeServerHandlerExecute singleExecutor) {
		for(int i=0;i<players.size();i++) {
			Player player = players.get(i);
			ClientData clientData = GameData.getSingleton().clientmap.get(player.clientId);
			Socket cSocket = clientData.getClientSocket();
			MainIO mainIo = GameData.getSingleton().mainiomap.get(player.clientId);
			mainIo.sendMessage(icommand, str);
//			try {
//				Thread.sleep(4000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	
	/**
	 * 找到除自己以外其它玩家
	 * @param roomId
	 * @param roomType
	 * @param clientId
	 * @return
	 */
	public static List<Player> getOtherPlayerInRoom(String roomId,String roomType,String clientId){
		List<Player> p = new ArrayList<Player>();
		System.out.println("roomId="+roomId+",roomType="+roomType+",clientId="+clientId);
		Room room = GameData.getSingleton().roommap.get(roomType).get(roomId);
		Map<Player, Integer> players = room.playermap;
		CommonTools.listMaps(players);
		for(Player player :players.keySet()) {
			if(!player.clientId.equals(clientId)) {
				p.add(player);
				System.out.println("playername="+player.playerName);
			}
		}
		return p;
	}
	
	/**
	 * 给玩家传输数据
	 * @param players
	 * @param icommand
	 * @param str
	 * @param singleExecutor
	 */                                                                                                                
	public synchronized static void sendClientsMessage(Map<Player,Integer> players,ICommand icommand,Map<String,String> mmaps,TimeServerHandlerExecute singleExecutor) {
		for(Player player:players.keySet()) {
			ClientData clientData = GameData.getSingleton().clientmap.get(player.clientId);
			Socket cSocket = clientData.getClientSocket();
			MainIO mainIo = GameData.getSingleton().mainiomap.get(player.clientId);
			Game game = ChuaChuaGameMainData.gameData.get(mmaps.get("roomId"));
			Game g = new Game();
			g.setBall_list(game.ball_list);
			g.setBoardPropsmap(game.boardPropsmap);
			g.setMyBrickList(game.myBrickList);
			g.setEnemyBrickList(game.enemyBrickList);
			g.setMyborad(game.myborad);
//			System.out.println("--------------------------="+players.get(player));
			if(players.get(player) == 1) { //座位一
				mmaps.put("type", "1");
				mmaps.put("Game", JsonTools.getString(game));
			}else if(players.get(player) == 2){
				mmaps.put("type", "2");
				double moveX = g.enemyborad.locX;
				g.enemyborad.locX = g.myborad.locX;
				g.myborad.locX = moveX;
				mmaps.put("Game", JsonTools.getString(g));
			}
//				mmaps.put("Game", JsonTools.getString(game));
			mainIo.sendMessage(icommand, JsonTools.getString(new Info("游戏数据",JsonTools.getData(mmaps))));
		}
	}
	
	/**
	 * 给玩家传输数据
	 * @param players
	 * @param icommand
	 * @param str
	 * @param singleExecutor
	 */                                                                                                                
	public static void sendClientsMessage(Map<Player,Integer> players,ICommand icommand,String str,TimeServerHandlerExecute singleExecutor) {
//		Iterator entries = players.entrySet().iterator(); 
//		while (entries.hasNext()) {
//			Map.Entry entry = (Map.Entry) entries.next();
//			Player player = (Player) entry.getKey();
//			ClientData clientData = GameData.getSingleton().clientmap.get(player.clientId);
//			Socket cSocket = clientData.getClientSocket();
//			MainIO mainIo = GameData.getSingleton().mainiomap.get(player.clientId);
//			mainIo.sendMessage(icommand, str);
//		}
		for(Player player:players.keySet()) {
			ClientData clientData = GameData.getSingleton().clientmap.get(player.clientId);
			Socket cSocket = clientData.getClientSocket();
			MainIO mainIo = GameData.getSingleton().mainiomap.get(player.clientId);
			mainIo.sendMessage(icommand, str);
		}
	}
	
	/**
	 * 改变房间内所有玩家的状态
	 * @param room
	 * @param data
	 * @param rState
	 * @param cState
	 * @param pState
	 */
	public static void changeRoomAllPlayersState(Room room,int rState,int cState,int pState) {
		//房间状态改变
		room.roomInfo.RoomState = rState;
		//房间内玩家状态改变
		Map<Player,Integer> players = room.playermap;
		Iterator entries = players.entrySet().iterator(); 
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			Player player = (Player) entry.getKey();
			player.setGamestate(pState) ;
			//客户端的玩家状态改变和客户端的状态改变
			ClientData clientData = GameData.getSingleton().clientmap.get(player.clientId);
			clientData.getPlayer().setGamestate(pState);
			clientData.setClientLocState(cState);
		}
	}
	
	//查看所有玩家,客户端,房间的状态
}

package tool;

import java.net.Socket;
import java.util.Iterator;
import java.util.Map;

import data.GameData;
import entity.IO.MainIO;
import entity.agrement.ICommand;
import entity.client.ClientData;
import entity.player.Player;
import entity.rooms.Room;
import main.TimeServerHandlerExecute;

/**
 * 全局传输数据类
 */
public class DataTransmitTools {
	
	/**
	 * 给所有玩家传输数据
	 * @param players
	 * @param icommand
	 * @param str
	 * @param singleExecutor
	 */
	public static synchronized void sendAllClientsMessage(Map<Player,Integer> players,ICommand icommand,String str,TimeServerHandlerExecute singleExecutor) {
		Iterator entries = players.entrySet().iterator(); 
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			Player player = (Player) entry.getKey();
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

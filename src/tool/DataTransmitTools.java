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
 * ȫ�ִ���������
 */
public class DataTransmitTools {
	
	/**
	 * ��������Ҵ�������
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
	 * �ı䷿����������ҵ�״̬
	 * @param room
	 * @param data
	 * @param rState
	 * @param cState
	 * @param pState
	 */
	public static void changeRoomAllPlayersState(Room room,int rState,int cState,int pState) {
		//����״̬�ı�
		room.roomInfo.RoomState = rState;
		//���������״̬�ı�
		Map<Player,Integer> players = room.playermap;
		Iterator entries = players.entrySet().iterator(); 
		while (entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			Player player = (Player) entry.getKey();
			player.setGamestate(pState) ;
			//�ͻ��˵����״̬�ı�Ϳͻ��˵�״̬�ı�
			ClientData clientData = GameData.getSingleton().clientmap.get(player.clientId);
			clientData.getPlayer().setGamestate(pState);
			clientData.setClientLocState(cState);
		}
	}
	
	//�鿴�������,�ͻ���,�����״̬
}

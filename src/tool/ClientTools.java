package tool;

import java.net.Socket;
import java.util.Random;

import org.junit.Test;

import config.ClientConfig;
import config.GameConfig;
import data.GameData;
import entity.client.ClientData;
import entity.player.Player;
import module.DbOperator;

/**
 * �ͻ��˷���
 */
public class ClientTools {
	
	public static GameData g = GameData.getSingleton();
	public static String clientThreadName;
	public static String clientThreadRName;
	public static String clientThreadSName;
	/**
	 *��ӿͻ��� 
	 */
	public static boolean addClient(Socket socket) {
		try {
			ClientData gameData = new ClientData(socket);
			String flag = gameData.getIp()+":"+gameData.getPort();
			//�ı�ͻ����е�״̬,δ��¼
			gameData.setClientLocState(ClientConfig.NOLOGIN);
			g.clientmap.put(flag,gameData);
			GameConfig.serverCount++;
			return true;
		}catch(Exception e) {
			new Exception("�ͻ���"+socket.getInetAddress()+":"+socket.getPort()+"���ʧ��");
			return false;
		}
	}
	
	/**
	 * ��ÿͻ��˵��߳���
	 * 	�߳�ip+:+port+:+1s 1��ʾ������ ,2��ʾ�ͻ��� s��ʾ���� r��ʾ����
	 */
	public static void initClientThreadName(Socket s) {
		clientThreadName = s.getInetAddress().toString().substring(1)+":"+s.getPort()+":"+"1";
		clientThreadRName = clientThreadName+"r";
		clientThreadSName = clientThreadName+"s";
	}
	
	/**
	 * ͨ����ǰ�߳������ĵ��б��е�keyֵ
	 * @param ThreadName
	 */
	public static String getKeyByThreadName(String ThreadName) {
		String[] str = ThreadName.split(":");
		return str[0]+":"+str[1];
	}
	
	/**
	 * ���ÿͻ��˵�״̬
	 * @param ThreadName
	 * @param state
	 */
	public static void setClientLocState(String ThreadName,int state) {
		String key = ClientTools.getKeyByThreadName(ThreadName);
		GameData.getSingleton().clientmap.get(key).setClientLocState(ClientConfig.LOGININHALL);
	}
	
	/**
	 * ���ÿͻ������
	 * @param ThreadName
	 * @param player
	 */
	public static void setClientPlayer(String ThreadName,Player player) {
		String key = ClientTools.getKeyByThreadName(ThreadName);
		GameData.getSingleton().clientmap.get(key).setPlayer(player);
	}
	
	/**
	 * ����ο�����
	 */
	public static String getGuestPeopleName() {
		//��ĸ����(����a~z����ĸ������)
		char[] str = new char [26*2+10];
		for(int i=0;i<26;i++) {
			str[i] = (char)(65+i);
		}
		for(int i=0;i<26;i++) {
			str[26+i] = (char)(97+i);
		}
		for(int i=0;i<10;i++) {
			str[52+i] = (i+"").charAt(0);
		}
		DbOperator dbOperator = new DbOperator(new DataBaseTools());
		Random rand = new Random();
		char[] name = new char[8];
		String GuestName;
		do {
			int t;
			for(int i=0;i<8;i++) {
				t=rand.nextInt(62);
				name[i] =str[t]; 
			}
			GuestName = new String(name);
		}while(dbOperator.judgePeopleNameExist(GuestName));
 		return GuestName;
	}
	
	@Test
	public void test() {
		System.out.println(getGuestPeopleName());
	}
}

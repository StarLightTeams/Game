package entity.client;

import java.net.Socket;

import entity.player.Player;
import tool.ClientTools;

/**
 * �ͻ�����Ϣ��
 */
public class ClientData {
	//�ͻ���ip
	private String ip;
	//�ͻ��˵�port
	private int port;
	//�ͻ���
	private Socket clientSocket ;
	//�Ƿ��¼
	private boolean islogin = false;
	//�ͻ���λ��״̬��0.δ����1.����δ��¼2.��¼�ڴ���3.����δ׼��4.����׼��5.��ʼ��Ϸ6.��Ϸ��������㻭�桿
	private int clientLocState = 0;
	//���
	public Player player;
	//�Ƿ���
	public boolean isLive=false;
	
	public ClientData() {
		
	}
	
	public ClientData(Socket clientSocket) {
		this.ip = clientSocket.getInetAddress().toString().substring(1);
		this.port = clientSocket.getPort();
		this.clientSocket = clientSocket;
	}
	
	
	
	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getClientLocState() {
		return clientLocState;
	}
	public void setClientLocState(int clientLocState) {
		this.clientLocState = clientLocState;
	}
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public Socket getClientSocket() {
		return clientSocket;
	}

	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	public boolean isIslogin() {
		return islogin;
	}

	public void setIslogin(boolean islogin) {
		this.islogin = islogin;
	}

	@Override
	public String toString() {
		return "ClientData [ip=" + ip + ", port=" + port + ", clientSocket=" + clientSocket + ", islogin=" + islogin
				+ ", clientLocState=" + clientLocState + ", player=" + player + ", isLive=" + isLive + "]";
	}
	
}

package entity.client;

import java.net.Socket;

import entity.player.Player;
import tool.ClientTools;

/**
 * 客户端信息类
 */
public class ClientData {
	//客户端ip
	private String ip;
	//客户端的port
	private int port;
	//客户端
	private Socket clientSocket ;
	//是否登录
	private boolean islogin = false;
	//客户端位置状态【0.未连接1.连接未登录2.登录在大厅3.房间未准备4.房间准备5.开始游戏6.游戏结束后结算画面】
	private int clientLocState = 0;
	//玩家
	public Player player;
	//是否存活
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

package entity.client;

import java.net.Socket;

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
	//客户端位置状态【0.未连接1.连接未登录2.登录在大厅3.房间为准备4.房间准备5.开始游戏6.游戏结束后结算画面】
	private int clientLocState = 0;
	
	public ClientData() {
		
	}
	
	public ClientData(Socket clientSocket) {
		this.ip = clientSocket.getInetAddress().toString().substring(1);
		this.port = clientSocket.getPort();
		this.clientSocket = clientSocket;
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

	
}

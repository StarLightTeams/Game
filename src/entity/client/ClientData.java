package entity.client;

import java.net.Socket;

public class ClientData {
	//客户端ip
	private String ip;
	//客户端的port
	private String port;
	//客户端
	private Socket clientSocket ;
	//是否登录
	private boolean islogin = false;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
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

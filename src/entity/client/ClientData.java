package entity.client;

import java.net.Socket;

public class ClientData {
	//�ͻ���ip
	private String ip;
	//�ͻ��˵�port
	private String port;
	//�ͻ���
	private Socket clientSocket ;
	//�Ƿ��¼
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

package entity.client;

import java.net.InetAddress;
import java.net.Socket;

public class ClientData {
	//�ͻ���ip
	private String ip;
	//�ͻ��˵�port
	private int port;
	//�ͻ���
	private Socket clientSocket ;
	//�Ƿ��¼
	private boolean islogin = false;
	
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

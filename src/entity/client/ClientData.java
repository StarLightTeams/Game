package entity.client;

import java.net.InetAddress;
import java.net.Socket;

public class ClientData {
	//�ͻ���ip
	private InetAddress ip;
	//�ͻ��˵�port
	private int port;
	//�ͻ���
	private Socket clientSocket ;
	//�Ƿ��¼
	private boolean islogin = false;
	
	public ClientData() {
		
	}
	
	public ClientData(Socket clientSocket) {
		this.ip = clientSocket.getInetAddress();
		this.port = clientSocket.getPort();
		this.clientSocket = clientSocket;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
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

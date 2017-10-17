package entity.client;

import java.net.Socket;

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
	//�ͻ���λ��״̬��0.δ����1.����δ��¼2.��¼�ڴ���3.����Ϊ׼��4.����׼��5.��ʼ��Ϸ6.��Ϸ��������㻭�桿
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

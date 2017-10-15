package config;

/*
 * ����������
 */
public class ServerConfig {

	private static ServerConfig instance;

	private ServerConfig() {
	}

	public static ServerConfig getInstance() {
		if (instance == null) {
			instance = new ServerConfig();
		}
		return instance;
	}
	//�˿�
	public int port = 10020;
	public int serverConnectionTime = 1000;//����ʱ��
	public int peopleConnectionCount = 50;//�����������
}

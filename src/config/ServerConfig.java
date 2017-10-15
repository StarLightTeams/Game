package config;

/*
 * 服务器配置
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
	//端口
	public int port = 10020;
	public int serverConnectionTime = 1000;//链接时间
	public int peopleConnectionCount = 50;//最大链接数量
}

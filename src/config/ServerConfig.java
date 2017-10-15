package config;

/*
 * ·şÎñÆ÷ÅäÖÃ
 * add by king 2017/10/15
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
	/*
	 * 
	 */
	public int serverConnectionTime = 1000;
	public int peopleConnectionCount = 50;
}

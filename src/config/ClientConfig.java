package config;

public class ClientConfig {
	//客户端位置状态【0.未连接1.连接未登录2.登录在大厅3.房间为准备4.房间准备5.开始游戏6.游戏结束后结算画面】
	//未连接
	public static final int NOCON = 0;
	//未登录
	public static final int NOLOGIN = 1;
	//登录在大厅
	public static final int LOGININHALL = 2;
	//房间未准备
	public static final int NOROOM = 3;
	//房间准备
	public static final int ROOMPREPARING = 4;
	//开始游戏
	public static final int GAMESTART = 5;
	//结束游戏
	public static final int GAMEEND = 6;
	
}

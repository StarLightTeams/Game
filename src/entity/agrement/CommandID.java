package entity.agrement;

public class CommandID {

	public final static int Unknown = 0x0000;//未知指令
	public final static int Heart = 0x0001;//心跳指令
	public final static int Connect = 0x0002;//连接协议
	public final static int GuestLogin = 0x0003;//游客登录
	public final static int Login = 0x0004;//用户登录
	public final static int LoginOut = 0x0005;//用户退出
	public final static int Register = 0x0006;//用户注册
	public final static int GeneralInformation = 0x0007;//普通信息
	public final static int GamePreparing = 0x0008;//游戏准备
	public final static int GamePreparingError =0x0009;//游戏准备时出错
	public final static int VerifyState = 0x0010;//验证所有玩家状态
	public final static int VerifyStateErr = 0x0011;//验证所有玩家状态失败
	public final static int GameLoading = 0x0012;//游戏加载完成
	public final static int WaitOtherPeople = 0x0013;//等待其他玩家
	public final static int GameStart = 0x0014;//游戏开始
	public final static int DisConnnect = 0x0015;//断开连接
	public final static int GameData = 0x0016;//游戏数据
}

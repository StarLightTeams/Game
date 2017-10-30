package tool.agreement;


import org.junit.Test;

import entity.agrement.CommandID;
import entity.agrement.ICommand;
import rule.agreement.ConnectCommand;
import rule.agreement.GeneralInformationCommand;
import rule.agreement.GuestLoginCommand;
import rule.agreement.HeartCommand;
import rule.agreement.LoginCommand;
import rule.agreement.LoginOutCommand;
import rule.agreement.RegisterCommand;
import rule.agreement.UnknownCommand;

/**
 * 协议工具
 */
public class AgreeMentTools {
	/**
	 * 判断协议类型,返回该协议实例
	 */
	public static ICommand judgeICommand(int id){
		if(id == CommandID.Connect){//连接协议
			return new ConnectCommand(id);
		}else if(id == CommandID.GuestLogin){//游客协议
			return new GuestLoginCommand(id);
		}else if(id == CommandID.Heart) {//心跳协议
			return new HeartCommand(id);
		}else if(id == CommandID.Login) {//登录协议
			return new LoginCommand(id);
		}else if(id == CommandID.Unknown){//未知数据协议
			return new UnknownCommand(id);
		}else if(id == CommandID.LoginOut) {//退出登录协议
			return new LoginOutCommand(id);
		}else if(id == CommandID.Register){//注册协议
			return new RegisterCommand(id);
		}else if(id == CommandID.GeneralInformation) {//普通信息协议
			return new GeneralInformationCommand(id);
		}else {
			//返回错误协议数据
			return new ICommand();
		}
	}
	
	/**
	 * 判断协议类型,返回该协议实例
	 */
	public static int judgeICommand(ICommand iCommand){
		if(iCommand.getClass().equals(ConnectCommand.class)){//连接协议
			return CommandID.Connect;
		}else if(iCommand.getClass().equals(GuestLoginCommand.class)){//游客协议
			return CommandID.GuestLogin;
		}else if(iCommand.getClass().equals(HeartCommand.class)) {//心跳协议
			return CommandID.Heart;
		}else if(iCommand.getClass().equals(LoginCommand.class)) {//登录协议
			return CommandID.Login;
		}else if(iCommand.getClass().equals(LoginOutCommand.class)) {//退出登录协议
			return CommandID.LoginOut;
		}else if(iCommand.getClass().equals(UnknownCommand.class)){//未知数据协议
			return CommandID.Unknown;
		}else if(iCommand.getClass().equals(RegisterCommand.class)){//注册协议
			return CommandID.Register;
		}else if(iCommand.getClass().equals(GeneralInformationCommand.class)) {//普通信息协议
			return CommandID.GeneralInformation;
		}else {
			//返回错误协议数据
			return -1;
		}
	}
	
	/**
	 * 获得协议数据
	 */
	public static ICommand getICommand(DataBuffer data) {
		ICommand iCommand = new ICommand();
		iCommand.ReadBufferIp(data);
		ICommand c = judgeICommand(iCommand.header.id);
		c.ReadFromBufferBody(data);
		return c; 
	}
	
	@Test
	public void test() {
		ConnectCommand connectCommand = new ConnectCommand();
		System.out.println(judgeICommand(connectCommand));
	}
}

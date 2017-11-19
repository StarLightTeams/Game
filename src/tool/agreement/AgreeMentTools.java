package tool.agreement;


import org.junit.Test;

import entity.agrement.CommandID;
import entity.agrement.ICommand;
import rule.agreement.ConnectCommand;
import rule.agreement.DisconnectCommand;
import rule.agreement.GameDataCommand;
import rule.agreement.GameLoadingCommand;
import rule.agreement.GamePreparingCommand;
import rule.agreement.GamePreparingErrorCommand;
import rule.agreement.GameStartCommand;
import rule.agreement.GeneralInformationCommand;
import rule.agreement.GuestLoginCommand;
import rule.agreement.HeartCommand;
import rule.agreement.LoginCommand;
import rule.agreement.LoginOutCommand;
import rule.agreement.RegisterCommand;
import rule.agreement.UnknownCommand;
import rule.agreement.VerifyStateCommand;
import rule.agreement.VerifyStateErrCommand;
import rule.agreement.WaitOtherPeopleCommand;

/**
 * Э�鹤��
 */
public class AgreeMentTools {
	/**
	 * �ж�Э������,���ظ�Э��ʵ��
	 */
	public static ICommand judgeICommand(int id){
		if(id == CommandID.Connect){//����Э��
			return new ConnectCommand(id);
		}else if(id == CommandID.GuestLogin){//�ο�Э��
			return new GuestLoginCommand(id);
		}else if(id == CommandID.Heart) {//����Э��
			return new HeartCommand(id);
		}else if(id == CommandID.Login) {//��¼Э��
			return new LoginCommand(id);
		}else if(id == CommandID.Unknown){//δ֪����Э��
			return new UnknownCommand(id);
		}else if(id == CommandID.LoginOut) {//�˳���¼Э��
			return new LoginOutCommand(id);
		}else if(id == CommandID.Register){//ע��Э��
			return new RegisterCommand(id);
		}else if(id == CommandID.GeneralInformation) {//��ͨ��ϢЭ��
			return new GeneralInformationCommand(id);
		}else if(id == CommandID.VerifyState) {//��֤ȫ�����״̬Э��
			return new VerifyStateCommand(id);
		}else if(id == CommandID.VerifyStateErr) {//��֤ȫ�����״̬����Э��
			return new VerifyStateErrCommand(id);
		}else if(id == CommandID.GamePreparing){//��Ϸ׼��Э��
			return new GamePreparingCommand(id);
		}else if(id == CommandID.GamePreparingError){//��Ϸ׼������Э��
			return new GamePreparingErrorCommand(id);
		}else if(id == CommandID.WaitOtherPeople) {//�ȴ��������Э��
			return new WaitOtherPeopleCommand(id);
		}else if(id == CommandID.GameStart){//��Ϸ��ʼЭ��
			return new GameStartCommand(id);
		}else if(id == CommandID.GameLoading){//��Ϸ����Э��
			return new GameLoadingCommand(id);
		}else if(id == CommandID.DisConnnect){//�Ͽ�����Э��
			return new DisconnectCommand(id);
		}else if(id == CommandID.GameData){
			return new GameDataCommand(id);
		}else {
			//���ش���Э������
			return new ICommand();
		}
	}
	
	/**
	 * �ж�Э������,���ظ�Э��ʵ��
	 */
	public static int judgeICommand(ICommand iCommand){
		if(iCommand.getClass().equals(ConnectCommand.class)){//����Э��
			return CommandID.Connect;
		}else if(iCommand.getClass().equals(GuestLoginCommand.class)){//�ο�Э��
			return CommandID.GuestLogin;
		}else if(iCommand.getClass().equals(HeartCommand.class)) {//����Э��
			return CommandID.Heart;
		}else if(iCommand.getClass().equals(LoginCommand.class)) {//��¼Э��
			return CommandID.Login;
		}else if(iCommand.getClass().equals(LoginOutCommand.class)) {//�˳���¼Э��
			return CommandID.LoginOut;
		}else if(iCommand.getClass().equals(UnknownCommand.class)){//δ֪����Э��
			return CommandID.Unknown;
		}else if(iCommand.getClass().equals(RegisterCommand.class)){//ע��Э��
			return CommandID.Register;
		}else if(iCommand.getClass().equals(GeneralInformationCommand.class)) {//��ͨ��ϢЭ��
			return CommandID.GeneralInformation;
		}else if(iCommand.getClass().equals(GamePreparingCommand.class)) {//��Ϸ׼��Э��
			return CommandID.GamePreparing;
		}else if(iCommand.getClass().equals(GamePreparingErrorCommand.class)) {//��Ϸ׼������Э��
			return CommandID.GamePreparingError;
		}else if(iCommand.getClass().equals(VerifyStateCommand.class)) {//��֤ȫ�����״̬Э��
			return CommandID.VerifyState;
		}else if(iCommand.getClass().equals(VerifyStateErrCommand.class)) {//��֤ȫ�����״̬����Э��
			return CommandID.VerifyStateErr;
		}else if(iCommand.getClass().equals(WaitOtherPeopleCommand.class)){//�ȴ��������Э��
			return CommandID.WaitOtherPeople;
		}else if(iCommand.getClass().equals(GameStartCommand.class)){//��Ϸ��ʼЭ��
			return CommandID.GameStart;
		}else if(iCommand.getClass().equals(GameLoadingCommand.class)){//��Ϸ����Э��
			return CommandID.GameLoading;
		}else if(iCommand.getClass().equals(DisconnectCommand.class)){//�Ͽ�����Э��
			return CommandID.DisConnnect;
		}else if(iCommand.getClass().equals(GameDataCommand.class)){//��Ϸ����Э��
			return CommandID.GameData;
		}else {
			//���ش���Э������
			return -1;
		}
	}
	
	/**
	 * ���Э������
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

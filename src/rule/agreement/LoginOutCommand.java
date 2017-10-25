package rule.agreement;

import entity.agrement.CommandID;
import entity.agrement.ICommand;

public class LoginOutCommand extends ICommand{
	
	public LoginOutCommand(){
		super(CommandID.LoginOut);
	}
	public LoginOutCommand(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
//	public void WriteBody(DataBuffer buffer)
//	{
//	}
//
//	public void ReadBody(DataBuffer buffer)
//	{
//	}
}

package rule.agreement;

import tool.agreement.DataBuffer;
import entity.agrement.CommandID;
import entity.agrement.ICommand;

public class LoginCommand extends ICommand{
	
	public LoginCommand(){
		super(CommandID.Login);
	}
	public LoginCommand(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public void WriteBody(DataBuffer buffer)
	{
	}

	public void ReadBody(DataBuffer buffer)
	{
	}
}

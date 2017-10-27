package rule.agreement;

import tool.agreement.DataBuffer;
import entity.agrement.CommandID;
import entity.agrement.ICommand;
/// ÓÎ¿ÍµÇÂ¼
public class GuestLoginCommand extends ICommand{

	public GuestLoginCommand(){
		super(CommandID.GuestLogin);
	}
	public GuestLoginCommand(int id) {
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

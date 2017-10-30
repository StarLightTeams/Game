package rule.agreement;

import entity.agrement.CommandID;
import entity.agrement.ICommand;

public class GeneralInformationCommand extends ICommand {
	
	public GeneralInformationCommand(){
		super(CommandID.GeneralInformation);
	}
	public GeneralInformationCommand(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
//	public void WriteBody(DataBuffer buffer,String str)
//	{
//		
//	}
//	public void ReadBody(DataBuffer buffer)
//	{
//		buffer.ReadChar();
//	}
}

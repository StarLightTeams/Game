package rule.agreement;

import entity.agrement.CommandID;
import entity.agrement.ICommand;

public class VerifyStateCommand extends ICommand {
	public VerifyStateCommand(){
		super(CommandID.VerifyState);
	}
	public VerifyStateCommand(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
//	public void WriteBody(DataBuffer buffer,String str)
//	{
//		buffer.WriteString(str);
//	}
//	public void ReadBody(DataBuffer buffer)
//	{
////		buffer.ReadChar();
//		body=buffer.ReadString();
//	}
}

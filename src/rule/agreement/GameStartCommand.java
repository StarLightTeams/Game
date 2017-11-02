package rule.agreement;

import entity.agrement.CommandID;
import entity.agrement.ICommand;

public class GameStartCommand extends ICommand {
	public GameStartCommand(){
		super(CommandID.GameStart);
	}
	public GameStartCommand(int id) {
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

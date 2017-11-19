package rule.agreement;

import entity.agrement.CommandID;
import entity.agrement.ICommand;

public class GameDataCommand extends ICommand{
	
//	public String body = "";
	public GameDataCommand(){
		super(CommandID.GameData);
	}
	public GameDataCommand(int id) {
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


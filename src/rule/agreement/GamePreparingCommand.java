package rule.agreement;

import entity.agrement.CommandID;
import entity.agrement.ICommand;

/**
 * 用户匹配房间协议
 */
public class GamePreparingCommand extends ICommand {
	public GamePreparingCommand(){
		super(CommandID.GamePreparing);
	}
	public GamePreparingCommand(int id) {
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

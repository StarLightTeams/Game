package rule.agreement;

import entity.agrement.CommandID;
import entity.agrement.ICommand;

/**
 * 游戏准备时出错协议
 */
public class GamePreparingErrorCommand extends ICommand{
	public GamePreparingErrorCommand(){
		super(CommandID.GamePreparingError);
	}
	public GamePreparingErrorCommand(int id) {
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

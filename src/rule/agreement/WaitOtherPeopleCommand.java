package rule.agreement;

import entity.agrement.CommandID;
import entity.agrement.ICommand;

/**
 * 等待其他玩家协议
 */
public class WaitOtherPeopleCommand extends ICommand{
	public WaitOtherPeopleCommand(){
		super(CommandID.WaitOtherPeople);
	}
	public WaitOtherPeopleCommand(int id) {
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

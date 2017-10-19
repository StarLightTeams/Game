package rule.agreement;

import tool.agreement.DataBuffer;
import entity.agrement.ICommand;
/// 连接协议
public class ConnectCommand extends ICommand{

	public ConnectCommand(int id) {
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

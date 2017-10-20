package rule.agreement;

import java.util.Arrays;

import tool.agreement.DataBuffer;
import entity.agrement.CommandID;
import entity.agrement.ICommand;
/// 连接协议
public class ConnectCommand extends ICommand{
	
	public String body = "";
	public ConnectCommand(){
		super(CommandID.Connect);
	}
	public ConnectCommand(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public void WriteBody(DataBuffer buffer)
	{
		buffer.WriteString("连接成功");
	}

	public void ReadBody(DataBuffer buffer)
	{
		body=buffer.getString();
	}
}

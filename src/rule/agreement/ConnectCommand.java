package rule.agreement;

import java.util.Arrays;

import tool.agreement.DataBuffer;
import entity.agrement.CommandID;
import entity.agrement.ICommand;
/// ����Э��
public class ConnectCommand extends ICommand{
	
	
	public ConnectCommand(){
		super(CommandID.Connect);
	}
	public ConnectCommand(int id) {
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

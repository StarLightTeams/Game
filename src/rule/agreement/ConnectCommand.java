package rule.agreement;

import java.util.Arrays;

import tool.agreement.DataBuffer;
import entity.agrement.CommandID;
import entity.agrement.ICommand;
/// ����Э��
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
		buffer.WriteString("���ӳɹ�");
	}

	public void ReadBody(DataBuffer buffer)
	{
		body=buffer.getString();
	}
}

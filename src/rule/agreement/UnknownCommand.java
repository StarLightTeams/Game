package rule.agreement;

import tool.agreement.DataBuffer;
import entity.agrement.ICommand;

public class UnknownCommand extends ICommand{

	public UnknownCommand(int id) {
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

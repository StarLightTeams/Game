package rule.agreement;

import tool.agreement.DataBuffer;
import entity.agrement.CommandID;
import entity.agrement.ICommand;

/// ĞÄÌøÖ¸Áî
public class HeartCommand extends ICommand{
	public HeartCommand(){
		super(CommandID.Heart);
	}
	public HeartCommand(int id) {
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

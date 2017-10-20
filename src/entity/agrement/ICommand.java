package entity.agrement;

import tool.agreement.DataBuffer;

public class ICommand {

	public CommandHeader header =new CommandHeader();
	public int pos;
	
	public ICommand(){
		
	}
	public ICommand(int id){
		header.id = id;
		header.length = 8;
	}
	
	public void WriteToBuffer(DataBuffer buffer){
		pos = buffer.Tell();
		buffer.WriteInt(header.length);
		buffer.WriteInt(header.id);
		WriteBody(buffer);
		int length = buffer.Tell() - pos;
		buffer.Seek(pos);
		buffer.WriteInt(length);
		buffer.Seek(pos + length);
	}
	public void WritheLength(DataBuffer buffer)
	{
		int length = buffer.Tell() - pos;
		buffer.Seek(pos);
		buffer.WriteInt(length);
		buffer.Seek(pos + length);
	}

	public void ReadFromBuffer(DataBuffer buffer)
	{
		System.out.println("ICommand::ReadFromBuffer(DataBuffer& buffer) \n");
		header.length = buffer.ReadInt();
		header.id = buffer.ReadInt();
		System.out.println("ICommand::ReadFromBuffer(DataBuffer& buffer) header.length = "+header.length+", header.id = "+ header.id);
		ReadBody(buffer);
	}
	public void WriteBody(DataBuffer buffer){};
	public void ReadBody(DataBuffer buffer){};
}

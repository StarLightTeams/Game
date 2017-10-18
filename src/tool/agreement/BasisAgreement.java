package tool.agreement;

/*
 * 基本协议类
 */
public abstract class  BasisAgreement {
	
	//协议ID
	public int XYID = 0;
	public int length  = 0;//数据包长度
	
	
	abstract void bisstream();//输出流
	abstract void bosstream();//输入流
}

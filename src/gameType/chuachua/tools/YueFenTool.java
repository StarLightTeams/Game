package gameType.chuachua.tools;

import org.junit.Test;

public class YueFenTool {

	public static double yueFen(double a){
		return ((int)(a*100000))*1.0/100000;
	}
	@Test
	public void test(){
		System.out.println(yueFen(10));
	}
}

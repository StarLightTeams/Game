package config.entity;

/**
 * 服务器注解类
 */
public class Log {
	
	/**
	 * 相当于	System.out.println(s);
	 * @param s
	 */
	public static void d(String s) {
		System.out.println(s);
	}
	
	/**
	 * 相当于	System.err.println(s);
	 * @param s
	 */
	public static void e(String s) {
		System.err.println(s);
	}
	/**
	 * 相当于	System.out.println();
	 */
	public static void d() {
		System.out.println();
	}
	/**
	 * 相当于	System.out.print(s);
	 * @param s
	 */
	public static void dn(String s) {
		System.out.print(s);
	}
	/**
	 * 相当于	System.out.println();
	 */
	public static void dn() {
		System.out.println();
	}
	
	/**
	 * 模块Log
	 */
	
}

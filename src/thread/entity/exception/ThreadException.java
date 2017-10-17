package thread.entity.exception;

/**
 * 捕获线程异常类
 */
public class ThreadException implements Thread.UncaughtExceptionHandler{

	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("线程["+t.getName()+":"+t.getId()+"]:"+e.getClass().getSimpleName()+"---"+e.getMessage());
	}
	
}

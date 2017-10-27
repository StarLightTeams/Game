package thread.entity;

import java.util.concurrent.ThreadFactory;

import thread.entity.exception.ThreadException;
import thread.inters.FactoryThreads;

/**
 * 线程工厂方法类(不用)
 * (创建可以捕获异常的线程,捕获异常时线程已经结束了)
 */
public class FactoryThread implements FactoryThreads{

	public Thread newThread(Runnable runnable, String name) {
		Thread thread = new Thread(runnable,name);
		thread.setUncaughtExceptionHandler(new ThreadException());
		System.out.println("线程["+thread.getName()+":"+thread.getId()+"]"+"创建成功");
		return thread;
	}
//	public Thread newThread(Runnable runnable) {
//		Thread thread = new Thread(runnable);
//		thread.setUncaughtExceptionHandler(new ThreadException());
//		System.out.println("线程["+thread.getName()+":"+thread.getId()+"]"+"创建成功");
//		return thread;
//	}
	
}

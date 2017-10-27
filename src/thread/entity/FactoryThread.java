package thread.entity;

import java.util.concurrent.ThreadFactory;

import thread.entity.exception.ThreadException;
import thread.inters.FactoryThreads;

/**
 * �̹߳���������(����)
 * (�������Բ����쳣���߳�,�����쳣ʱ�߳��Ѿ�������)
 */
public class FactoryThread implements FactoryThreads{

	public Thread newThread(Runnable runnable, String name) {
		Thread thread = new Thread(runnable,name);
		thread.setUncaughtExceptionHandler(new ThreadException());
		System.out.println("�߳�["+thread.getName()+":"+thread.getId()+"]"+"�����ɹ�");
		return thread;
	}
//	public Thread newThread(Runnable runnable) {
//		Thread thread = new Thread(runnable);
//		thread.setUncaughtExceptionHandler(new ThreadException());
//		System.out.println("�߳�["+thread.getName()+":"+thread.getId()+"]"+"�����ɹ�");
//		return thread;
//	}
	
}

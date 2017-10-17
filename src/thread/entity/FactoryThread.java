package thread.entity;

import thread.entity.exception.ThreadException;
import thread.inters.FactoryThreads;

/**
 * �̹߳���������
 * (�������Բ����쳣���߳�,�����쳣ʱ�߳��Ѿ�������)
 */
public class FactoryThread implements FactoryThreads{

	public Thread newThread(Runnable runnable, String name) {
		Thread thread = new Thread(runnable,name);
		thread.setUncaughtExceptionHandler(new ThreadException());
		System.out.println("�߳�["+thread.getName()+":"+thread.getId()+"]"+"�����ɹ�");
		return thread;
	}
	
}

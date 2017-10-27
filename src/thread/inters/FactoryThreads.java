package thread.inters;

/**
 * 线程工厂接口(不用)
 */
public interface FactoryThreads {
	public Thread newThread(Runnable runnable, String name) ;
}

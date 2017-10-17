package thread.inters;

public interface FactoryThreads {
	Thread newThread(Runnable runnable,String id);
}

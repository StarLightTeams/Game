package main;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerHandlerExecute {

	private ExecutorService executeor;
	public TimeServerHandlerExecute(int maxPoolSize ,int queueSize){
		this.executeor =new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
				maxPoolSize,120L,TimeUnit.SECONDS,new ArrayBlockingQueue<java.lang.Runnable>(queueSize));
	}
	public void excute(java.lang.Runnable task){
		executeor.execute(task);
	}
}

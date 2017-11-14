package main;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import data.GameData;

public class TimeServerHandlerExecute {

//	public TimeServerHandlerExecute(int maxPoolSize ,int queueSize){
//		this.executeor =new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
//				maxPoolSize,120L,TimeUnit.SECONDS,new ArrayBlockingQueue<java.lang.Runnable>(queueSize));
//	}
//	public void excute(java.lang.Runnable task){
//		executeor.execute(task);
//	}
	
	// 单例模式 双重校验锁
	public static ExecutorService executeor;
	
	
	public TimeServerHandlerExecute(int roomCount) {
		int maxPoolSize = roomCount*4*2+2;
		executeor = Executors.newFixedThreadPool(maxPoolSize);
//		executeor = Executors.newCachedThreadPool();
	}
	
//	public void excute(Callable<String> task) {
//		 Future<?> funture = executeor.submit(task);
//		 try {
//			 funture.get();
//		} catch (InterruptedException e) {
//			Throwable cause = e.getCause();
//			cause.printStackTrace();
//		} catch (ExecutionException e) {
//			Throwable cause = e.getCause();
//			cause.printStackTrace();
//		}
//	}
	
	public void excute(Runnable task) {
		//管理终止的方法，以及可为跟踪一个或多个异步任务执行状况 Future
		 Future<?> funture = executeor.submit(task);
		 System.out.println("task="+task.getClass().getName());
		 try {
			 if(!task.getClass().getName().equals("entity.IO.MainIO$receiveThread")) {
				 while(true) {
					 if(funture.isDone() && !funture.isCancelled()) {
//						 System.out.println("funture.isDone()="+funture.isDone());
						 System.out.println("funture.get()="+funture.get());
						 break;
					 }else {
						 Thread.sleep(10);
					 }
				 }
			 }
		} catch (InterruptedException e) {
			Throwable cause = e.getCause();
			cause.printStackTrace();
		} catch (ExecutionException e) {
			Throwable cause = e.getCause();
			cause.printStackTrace();
		}
	}
	
//	@Test
//	public void test() {
//		TimeServerHandlerExecute.executeor =Executors.newFixedThreadPool(10);
//		TimeServerHandlerExecute.excute(new Runnable() {
//			public void run() {
//				int i=0;
//				String[] t = new String[5];
//				while(i<10) {
//					System.out.println("hello");
//					i++;
//					t[i]="hello";
//				}
//			}
//		});
//	}
	
}

package tool;

import java.util.ArrayList;


public class ThreadTools {
	/**
	 * 移除线程名为clientId的线程
	 * @param clientId
	 */
	public static void remove(String clientId) {
		Thread[] lists = findAllThreads();
		for(Thread t:lists) {
			int index;
			if((index = t.getName().lastIndexOf("-"))!=-1) {
				String clientName = t.getName().substring(0,index );
				if(clientName.equals(clientId)) {
					t.interrupt();
				}
			}
		}
	}
	
	/**
	 * 找到所有线程
	 * @return
	 */
	public static Thread[] findAllThreads() {  
        ThreadGroup group =   
            Thread.currentThread().getThreadGroup();  
        ThreadGroup topGroup = group;  
        // traverse the ThreadGroup tree to the top  
        while ( group != null ) {  
            topGroup = group;  
            group = group.getParent();  
        }  
        // Create a destination array that is about  
        // twice as big as needed to be very confident  
        // that none are clipped.  
        int estimatedSize = topGroup.activeCount() * 2;  
        Thread[] slackList = new Thread[estimatedSize];  
        // Load the thread references into the oversized  
        // array. The actual number of threads loaded   
        // is returned.  
        int actualSize = topGroup.enumerate(slackList);  
        // copy into a list that is the exact size  
        Thread[] list = new Thread[actualSize];  
        System.arraycopy(slackList, 0, list, 0, actualSize);
        ArrayList<Thread> list2 = new ArrayList<Thread>();
        for(Thread t:list) {
        	if(("main").equals(t.getThreadGroup().getName())) {
        		list2.add(t);
        	}
        }
        return list2.toArray(new Thread[list2.size()]);  
    }  
}

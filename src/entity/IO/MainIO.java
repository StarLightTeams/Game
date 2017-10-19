package entity.IO;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

import config.entity.Log;
import thread.entity.FactoryThread;
import thread.entity.exception.ThreadException;
import tool.ClientTools;

/**
 * 主线程中的输出输入流类
 */
public class MainIO {
	
	public Socket clientSocket;
	public Thread send;
	public Thread receive;
	public OutputStream os;
	public InputStream is;
	
	public MainIO(Socket clientSocket) {
		this.clientSocket = clientSocket;
		try {
			is = clientSocket.getInputStream();
			os = clientSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送信息
	 * @param str
	 */
	public void sendMessage(String str) {
		send = new FactoryThread().newThread(new sendThread(str),ClientTools.clientThreadSName);
		send.setUncaughtExceptionHandler(new ThreadException());
		send.start();
	}
	
	/**
	 * 接受信息
	 */
	
	public void receiveMessage() {
		receive = new FactoryThread().newThread(new receiveThread(),ClientTools.clientThreadRName);
		receive.setUncaughtExceptionHandler(new ThreadException());
		receive.start();
	}
	
	/**
	 * 发送线程
	 */
	public class sendThread implements Runnable{
		
		String str = "";
		public sendThread() {
		}
		public sendThread(String str) {
			this.str = str;
		}
		public synchronized void run() {
			while(true) {
				//传输json数据
				try {
					Log.d("["+Thread.currentThread().getName()+"]="+str);
					os.write(str.getBytes());
					os.flush();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 接收线程
	 */
	public class receiveThread implements Runnable{
		public synchronized void run() {
			while(true) {
				try {
					byte[] b = new byte[1024];
					int len=is.read(b);
					Log.d("["+Thread.currentThread().getName()+"]="+new String(b));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}

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

/**
 * ���߳��е������������
 */
public class MainIO {
	//�߳�ip+:+port+:+1s 1��ʾ������ ,2��ʾ�ͻ��� s��ʾ���� r��ʾ����
	public String name;
	//1��ʾ������ ,2��ʾ�ͻ���
	public String nameFlag = "1";
	public Socket clientSocket;
	public Thread send;
	public Thread receive;
	public OutputStream os;
	public InputStream is;
	
	public MainIO(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.name = clientSocket.getInetAddress().toString().substring(1)+":"+clientSocket.getPort()+":"+nameFlag;
		try {
			is = clientSocket.getInputStream();
			os = clientSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ������Ϣ
	 * @param str
	 */
	public void sendMessage(String str) {
		send = new FactoryThread().newThread(new sendThread(str),name+"s");
		send.setUncaughtExceptionHandler(new ThreadException());
		send.start();
	}
	
	/**
	 * ������Ϣ
	 */
	
	public void receiveMessage() {
		receive = new FactoryThread().newThread(new receiveThread(),name+"r");
		receive.setUncaughtExceptionHandler(new ThreadException());
		receive.start();
	}
	
	/**
	 * �����߳�
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
				//����json����
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
	 * �����߳�
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

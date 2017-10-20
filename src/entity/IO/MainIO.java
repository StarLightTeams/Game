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
import entity.agrement.ICommand;
import thread.entity.FactoryThread;
import thread.entity.exception.ThreadException;
import tool.ClientTools;
import tool.agreement.DataBuffer;

/**
 * ���߳��е������������
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
	 * ������Ϣ
	 * @param str
	 */
	public void sendMessage(ICommand iCommand,String str) {
		send = new FactoryThread().newThread(new sendThread(iCommand,str),ClientTools.clientThreadSName);
		send.setUncaughtExceptionHandler(new ThreadException());
		send.start();
	}
	
	/**
	 * ������Ϣ
	 */
	public void receiveMessage() {
		receive = new FactoryThread().newThread(new receiveThread(),ClientTools.clientThreadRName);
		receive.setUncaughtExceptionHandler(new ThreadException());
		receive.start();
	}
	
	/**
	 * �����߳�
	 */
	public class sendThread implements Runnable{
		
		String str = "";
		ICommand iCommand;
		public sendThread() {
		}
		public sendThread(ICommand iCommand,String str) {
			this.str = str;
			this.iCommand = iCommand;
		}
		public synchronized void run() {
			while(true) {
				//����json����
				try {
					DataBuffer data= createAgreeMentMessage(iCommand,str);
					data.ReadChars();
					Log.d("["+Thread.currentThread().getName()+"]="+str+","+data.getCharsLength());
					os.write(data.readByte());
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
					DataBuffer data = getAgreeMentMessage(b);
					Log.d("["+Thread.currentThread().getName()+"]="+new String(data.buffer));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ����Э����Ϣ
	 * @param iCommand
	 * @param str
	 * @return
	 */
	public DataBuffer createAgreeMentMessage(ICommand iCommand,String str){
		DataBuffer data = new DataBuffer();
		iCommand.WriteToBuffer(data,str);
		return data;
	}
	
	/**
	 * ����Э����Ϣ
	 * @param bytes
	 * @return
	 */
	public DataBuffer getAgreeMentMessage(byte[] bytes) {
		DataBuffer data = new DataBuffer();
		data.getChars(bytes);
		return data;
	}
	

}

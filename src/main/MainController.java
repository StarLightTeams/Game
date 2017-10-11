package main;

import java.net.ServerSocket;
import java.net.Socket;

import config.GameConfig;



public class MainController {
	//服务
	ServerSocket server = null;
	TimeServerHandlerExecute singleExecutor =new TimeServerHandlerExecute(50,1000);
	Socket socket =null;
	
	private boolean startserver() throws Exception{
		
		server = new ServerSocket(GameConfig.port);
		System.out.println("----------------【服务器开启监控端口:--------------"+GameConfig.port+"】");
		System.out.println("----------------【等待客户机链接中】---------------");
		return true;
	}

	public void waitConnection(){
		while(true){
			try{
				socket=server.accept();
				
			}catch(Exception e){
				System.out.println("客户端连接发生异常");
			}
		}
	}
	
	public static void main(String[] args) {
		MainController controller =new MainController();
		try{
			if(controller.startserver()){
				
			}
		}catch(Exception e){
			System.out.println("服务器发生错误");
		}
	}
}

package entity.client;

import java.net.InetAddress;
import java.util.ArrayList;

import config.GameConfig;

/**
 * ip中端口的信息
 *
 */

public class ClientPortData {
	//端口的数量
	public int portCount;
	//端口号
	public ArrayList<Integer> ports = new ArrayList<Integer>();
	
	
	public ClientPortData() {
	}


	/**
	 * 增加端口
	 * @param port
	 * @return
	 */
	public boolean addPort(int port){
		if(ports.add(port)) {
			portCount++;
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 删除端口
	 * @param port
	 * @return
	 */
	public boolean deletePort(int port) {
		if(ports.remove((Object)port)) {
			portCount--;
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 判断是否到达一个ip最大端口数
	 * @return
	 */
	public boolean judgePortCount() {
		if(portCount>=GameConfig.clientPeopleCount){
			return false;
		}else {
			return true;
		}
	}
}

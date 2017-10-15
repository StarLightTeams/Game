package entity.client;

import java.net.InetAddress;
import java.util.ArrayList;

import config.GameConfig;

/**
 * ip�ж˿ڵ���Ϣ
 *
 */

public class ClientPortData {
	//�˿ڵ�����
	public int portCount;
	//�˿ں�
	public ArrayList<Integer> ports = new ArrayList<Integer>();
	
	
	public ClientPortData() {
	}


	/**
	 * ���Ӷ˿�
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
	 * ɾ���˿�
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
	 * �ж��Ƿ񵽴�һ��ip���˿���
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

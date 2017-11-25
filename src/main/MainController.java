package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import org.junit.Test;

import rule.agreement.ConnectCommand;
import rule.agreement.GameDataBoardCommand;
import rule.agreement.GuestLoginCommand;
import thread.entity.view.ThreadViewer;
import tool.ClientTools;
import tool.CommonTools;
import tool.DataTransmitTools;
import tool.RoomTools;
import tool.JsonTools;
import tool.agreement.DataBuffer;
import config.GameConfig;
import config.ServerConfig;
import config.entity.Log;
import data.GameData;
import entity.FactoryRooms.FactoryDoubleRoom;
import entity.FactoryRooms.FactoryFourRoom;
import entity.IO.MainIO;
import entity.agrement.CommandID;
import entity.agrement.ICommand;
import entity.client.ClientData;
import entity.client.ClientPortData;
import entity.info.Info;
import entity.rooms.DoubleRoom;
import entity.rooms.FourRoom;
import entity.rooms.Room;
import gameType.chuachua.data.ChuaChuaGameMainData;
import gameType.chuachua.entity.Ball;
import gameType.chuachua.entity.Game;

/**
 * ����������
 */
public class MainController {
	// ����
	ServerSocket server = null;
	// ����������
	ServerConfig sc = ServerConfig.getInstance();
	//�̳߳��߳�(һ������һ���̳߳�)
	List<TimeServerHandlerExecute> threadRoomsPoolList = new ArrayList<TimeServerHandlerExecute>();
	TimeServerHandlerExecute singleExecutor = new TimeServerHandlerExecute(sc.roomCount);
	Socket socket = null;
	// ��ȡ����
	GameData g = GameData.getSingleton();

	private boolean startserver() throws Exception {
		initRooms();
		server = new ServerSocket(sc.port);
		Log.d("----------------��������������ض˿�:" + sc.port + "��--------------");
		Log.d("----------------���ȴ��ͻ��������С�---------------");
		waitConnection();
		return true;
	}

	public void waitConnection() {
		while (true) {
			try {
				socket = server.accept();
				// �пͻ�������,ÿ�յ�������socket����ͻ����б���
				String ip = socket.getInetAddress().toString().substring(1);
				int port = socket.getPort();
				// ����б��д��ڴ�ip
				ClientPortData clientportdata;
				if (g.userclientmap.containsKey(ip)) {
					clientportdata = g.userclientmap.get(ip);
				} else {
					clientportdata = new ClientPortData();
				}

				clientportdata.addPort(port);
				// �ж��Ƿ񵽴�˿�����
				if (clientportdata.judgePortCount()) {
					g.userclientmap.put(ip, clientportdata);
					if (ClientTools.addClient(socket)) {
						Log.d("�ͻ���:" + ip + "-" + port + "��ӳɹ�");
						Log.d("�������еĿͻ���:");
						for (Map.Entry<String, ClientData> entry : g.clientmap.entrySet()) {
							Log.d("�ͻ���:" + entry.getValue().getIp() + "-" + entry.getValue().getPort());
						}
						Log.d("----------------------------------");

						// �õ���ǰ�ͻ��˵Ľ�,���߳���
						ClientTools.initClientThreadName(socket);
						// ��������,�����߳�
						MainIO mainIO = null;
						if(g.mainiomap.containsKey(ip+"-"+port)) {
							mainIO = g.mainiomap.get(ip+"-"+port);
						}else {
							mainIO = new MainIO(g.clientmap.get(ip + "-" + port).getClientSocket(),singleExecutor);
						}
						//���������߳�
//						mainIO.startHeartThread();
						Map<String,String> maps = new HashMap<String, String>();
						maps.put("cIp", ip);
						maps.put("cPort", port+"");
						if(!g.mainiomap.containsKey(ip+"-"+port)) {
							g.mainiomap.put(ip + "-" + port, mainIO);
						}
						
						mainIO.sendMessage(new ConnectCommand(), JsonTools.getString(new Info("���ӳɹ�",JsonTools.getData(maps))));
						mainIO.receiveMessage();
						
					} else {
						Log.d("�ͻ���:" + ip + "-" + port + "���ʧ��");
					}
				} else {
					Log.d("�ͻ���:" + ip + "�û��ﵽ����");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("�ͻ������ӷ����쳣");
			}
		}
	}

	/**
	 * ��ʼ������
	 */
	public void initRooms() {

		// ����������ͨ�����б�
		// ��ʼ����������
		g.roommap.put(RoomTools.createRoomType(2, GameConfig.doubleCommonGame), new HashMap<String, Room>());
		g.roommap.put(RoomTools.createRoomType(4, GameConfig.fourCommonGame), new HashMap<String, Room>());
		// ˫����ͨ��
		for (int i = 0; i < GameConfig.doubleRoomCount; i++) {
			String roomId = RoomTools.createRoomID(2, i);
			DoubleRoom dr = new FactoryDoubleRoom().createRoom(roomId);
			RoomTools.insertTable(g.roommap, RoomTools.createRoomType(2, GameConfig.doubleCommonGame), roomId, dr);
		}
		// ������ͨ��
		for (int i = 0; i < GameConfig.fourRoomCount; i++) {
			String roomId = RoomTools.createRoomID(4, i);
			FourRoom fr = new FactoryFourRoom().createRoom(roomId);
			RoomTools.insertTable(g.roommap, RoomTools.createRoomType(4, GameConfig.fourCommonGame), roomId, fr);
		}
		

	}

	public static void main(String[] args) {
		//�򿪲鿴�߳�
		new Thread(new Runnable() {
			public void run() {
				 JFrame f = ThreadViewer.createFramedInstance();  
			        // For this example, exit the VM when the viewer  
			        // frame is closed.  
			        f.addWindowListener(new WindowAdapter() {  
			                public void windowClosing(WindowEvent e) {  
			                    System.exit(0);  
			                }  
			            });  
			        // Keep the main thread from exiting by blocking  
			        // on wait() for a notification that never comes.  
			        Object lock = new Object();  
			        synchronized ( lock ) {  
			            try {  
			                lock.wait();  
			            } catch ( InterruptedException x ) {  
			            }  
			        }  
			}
		},"ThreadView").start();
		//������
		MainController controller = new MainController();
		try {
			if (controller.startserver()) {
				
			}
		} catch (Exception e) {
			Log.d("��������������");
			e.printStackTrace();
		}
	}
	
	

	@Test
	public void test() {
		ConnectCommand conect = new ConnectCommand();

		DataBuffer data = createAgreeMentMessage(conect, "hello111");

		byte[] b = new byte[10240];
				b=data.readByte();// �ָ�
		// -------------------------------
		// DataBuffer buffer1 =new DataBuffer();
		// buffer1.getChars(b);
		// conect.ReadFromBuffer(buffer1);

		DataBuffer data1 = getAgreeMentMessage(b);
		ICommand icommand = new ICommand();
		icommand.ReadBufferIp(data1);
		System.out.println(icommand.header.id);
		System.out.println(icommand.header.length);
		if (icommand.header.id == CommandID.Connect) {
			ConnectCommand conect2 = new ConnectCommand();
			conect2.header.id = icommand.header.id;
			conect2.header.length = icommand.header.length;
			conect2.ReadFromBufferBody(data1);
			System.out.print(conect2.body);
		}
	}

	/**
	 * ����Э����Ϣ
	 * 
	 * @param iCommand
	 * @param str
	 * @return
	 */
	public DataBuffer createAgreeMentMessage(ICommand iCommand, String str) {
		DataBuffer data = new DataBuffer();
		iCommand.WriteToBuffer(data, str);
		return data;
	}

	/**
	 * ����Э����Ϣ
	 * 
	 * @param bytes
	 * @return
	 */
	public DataBuffer getAgreeMentMessage(byte[] bytes) {
		DataBuffer data = new DataBuffer();
		char[] c = data.getChars(bytes);
		return data;
	}

}

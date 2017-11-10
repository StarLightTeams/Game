package tool;


import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import javax.security.auth.Subject;

import org.junit.Test;

import config.GameConfig;
import config.entity.Log;
import entity.info.Info;
import entity.player.Player;
import entity.rooms.DoubleRoom;
import entity.rooms.Room;
import entity.rooms.RoomInfo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import rule.RoomRule;

/**
 * json工具类
 */
public class JsonTools {
	
	/**
	 * 只能解析格式为{"key":"value","key1":"value1","key2":"value2",...} 且key和value只能是单纯字母或数字字符串
	 * @param str
	 * @return
	 */
	public static Map<String,String> parseData(String str){
		String[] strs = str.split(",");
		Map<String,String> maps = new HashMap<String, String>();
		for(int i=0;i<strs.length;i++) {
			String[] sstrs = strs[i].split("\"");
//			for(int j=0;j<sstrs.length;j++) {
//				System.out.println(sstrs[j]);
//			}
			maps.put(sstrs[1], sstrs[3]);
		}
		return maps;
	}
	
	/**
	 * 只能解析格式为{"key":{},"key1":{},"key2":"value2",...} 
	 * @param str
	 * @return
	 */
	public static Map<String,String> pasreObjectData(String str){
//		String[] strs = str.split("{");
		str = str.substring(1,str.length()-1);
		
//		System.out.println(str.length());
		
		Map<Integer,String> sInfo = new HashMap<Integer, String>();
		Map<Integer,Map<Integer,Integer>> lrInfo = new HashMap<Integer, Map<Integer,Integer>>();
		
		Stack<Character> stack = new Stack<Character>();
		int leftIndexs=0;
		int rightIndexs=0;
		int rr=0;
		int t=0;
		String strs = str;
		while((leftIndexs = findStrIndex(strs,"{"))!=-1) {
			rightIndexs = findStrLastIndex(strs);
			strs = strs.substring(rightIndexs+1,strs.length());
			Map<Integer, Integer> maps = new HashMap<Integer, Integer>();
			maps.put(leftIndexs+rr,rightIndexs+rr);
			rr = rightIndexs+1;
			lrInfo.put(t,maps);
//			System.out.println("l="+leftIndexs+",r="+rightIndexs);
			t++;
		}
		
		for(int i=0;i<lrInfo.size();i++) {
			Map<Integer,Integer> ms = lrInfo.get(i);
			for(Integer x:ms.keySet()) {
				String s = str.substring(x+1,ms.get(x));
//				System.out.println(s);
				sInfo.put(i,s);
			}
		}
		
		String sstrs ="";
		int b=0;
		int e=0;
		for(int i=0;i<lrInfo.size();i++) {
			Map<Integer,Integer> ms = lrInfo.get(i);
			for(Integer x:ms.keySet()) {
				e = x+1;
				sstrs+=str.substring(b,e);
				b = ms.get(x);
			}
		}
		sstrs+=str.substring(b,str.length());
//		System.out.println(sstrs);
		
		
		String[] sstrss = sstrs.split(",");
		
		String sst="";
		Map<String,String> ssMaps = new HashMap<String, String>();
		int kk=0;
		for(int i=0;i<sstrss.length;i++) {
			String[] ssss = sstrss[i].split("\\:");
			System.out.println("ssss[1]="+ssss[1]);
			if(ssss[1].equals("{}")) {
//				System.out.println(sInfo.get(kk));
				ssMaps.put(ssss[0].substring(1, ssss[0].length()-1), "{"+sInfo.get(kk)+"}");
				kk++;
			}else {
				ssMaps.put(ssss[0].substring(1, ssss[0].length()-1), ssss[1].substring(1, ssss[0].length()-1));
				sst = ssss[0].substring(1, ssss[0].length()-1);
			}
		}
		CommonTools.listMaps(ssMaps);
		return ssMaps;
	}
	
	public static int findStrLastIndex(String str) {
		int r=-1;
		Stack<Character> stack = new Stack<Character>();
		char[] c = str.toCharArray();
		for(int i=0;i<c.length;i++) {
			if(c[i]=='{') {
				stack.push(c[i]);
			}else if (c[i]=='}'){
				stack.pop();
				if(stack.empty()) {
					r = i;
					break;
				}
			}
		}
		return r;
	}

	public static int findStrIndex(String str,String s){
		int index = str.indexOf(s);
		return index;
	}
	
	/**
	 * 根据map的key和value得到json的String
	 * @param mStr
	 * @return
	 */
	public static String getData(Map<String,String> mStr) {
		JSONObject jObject = new JSONObject();
		for(Map.Entry<String, String> entry :mStr.entrySet()) {
			jObject.put(entry.getKey(),entry.getValue());
		}
		return jObject.toString();
	}
	
	public static String getString(Object obj) {
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("YYYY-MM-dd hh:mm:ss"));
		JSONObject jObject = new JSONObject();
//		jObject.put("className",getClassName(obj));
//		jObject.put("superName", getSuperClassName(obj));
//		jObject.put("data", obj);
		Map maps = new HashMap<Object, Object>();
		maps.put("className",getClassName(obj));
		maps.put("superName", getSuperClassName(obj));
		maps.put("data", obj);
		jObject.putAll(maps, jsonConfig);
		return jObject.toString();
	}
	
	public static Object parseJson(String str) {
		  
		JSONObject jObject = JSONObject.fromObject(str);
		String className = jObject.getString("className");
		String superName = jObject.getString("superName");
		if("Player".equals(className)) {
			Player player = new Player();
			player.setPlayerName(jObject.getJSONObject("data").getString("playerName"));
			player.setPassword(jObject.getJSONObject("data").getString("password"));
			player.setLoginState(Integer.parseInt(jObject.getJSONObject("data").getString("loginState")));
			player.setPlayerNo(Integer.parseInt(jObject.getJSONObject("data").getString("playerNo")));
			return player;
		}else if("Info".equals(className)){
			Info info = new Info();
			info.setHeadInfo(jObject.getJSONObject("data").getString("headInfo"));
			info.setDataInfo(jObject.getJSONObject("data").getString("dataInfo"));
			return info;
		}else if("Room".equals(superName)){
			try {
				Room room = new Room();
				RoomInfo roomInfo = new RoomInfo();
				JSONObject rInfo = jObject.getJSONObject("data").getJSONObject("roomInfo");
				roomInfo.roomId = rInfo.getString("roomId");
				roomInfo.createRoomTime = new SimpleDateFormat("YYYY-DD-MM hh:mm:ss").parse(rInfo.getString("createRoomTime"));
				roomInfo.roomType = rInfo.getString("roomType");
				roomInfo.RoomPeopleCount = rInfo.getInt("roomPeopleCount");
				roomInfo.RoomState = rInfo.getInt("roomState");
				roomInfo.RoomCreateTime = !rInfo.getString("roomCreateTime").equals("")?new SimpleDateFormat("YYYY-DD-MM hh:mm:ss").parse(rInfo.getString("roomCreateTime")):null;
				roomInfo.roomPLevel = rInfo.getInt("roomPLevel");
				roomInfo.endOfLoadingGame = rInfo.getInt("endOfLoadingGame");
				room.roomInfo = roomInfo;
				JSONObject pMap = jObject.getJSONObject("data").getJSONObject("playermap");
				Map<Player,Integer> playermap = new HashMap<Player,Integer>();
				Iterator entries = pMap.entrySet().iterator();
				while(entries.hasNext()) {
					Map.Entry entry = (Map.Entry) entries.next();
					String string = (String) entry.getKey();
					System.out.println("string="+string);
					String jsonStr = getJsonByString(string,"Player");
//					System.out.println(jsonStr);
					JSONObject pj = JSONObject.fromObject(jsonStr);
					JSONObject playerJb = pj.getJSONObject("Player");
					Player player = new Player();
					player.clientId = playerJb.getString("clientId");
					player.playerNo = playerJb.getInt("playerNo");
					JSONObject djmapJ = playerJb.getJSONObject("djmap");
					Iterator entries2 = djmapJ.entrySet().iterator();
					Map<String,Integer> djmap = new HashMap<String, Integer>();
					while(entries2.hasNext()) {
						Map.Entry entry2 = (Map.Entry) entries2.next();
						String pId = (String) entry.getKey();
						int pos = (Integer) entry.getValue();
//						System.out.println("pId="+pId);
						djmap.put(pId,pos);
					}
					player.djmap = djmap;
					player.playerName = playerJb.getString("playerName").equals("")?"":playerJb.getString("playerName");
					player.loginState = Integer.parseInt(playerJb.getString("loginState"));
//					System.out.println(player.toString());
					int t = (Integer) entry.getValue();
//					System.out.println(t);
					playermap.put(player, t);
				}
				room.playermap = playermap;
				JSONObject rRule = jObject.getJSONObject("data").getJSONObject("roomRule");
				RoomRule roomRule = new RoomRule();
				roomRule.RoomXXRule = rRule.getInt("roomXXRule");
				roomRule.RoomCostCardCount = rRule.getInt("roomCostCardCount");
				room.roomRule = roomRule;
				return room;
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("日期string转到data异常");
			}
			return null;
		}else {
			return null;
		}
		
	}
	
	/**
	 * 根据字符串得到类型,字符串格式[playerNo=0, playerName=admin, password=admin, playerCard=0, djmap=null, loginState=0]
	 * @param str
	 * @param type
	 * @return
	 * "playermap":{"Player [playerNo=0, playerName=admin, password=admin, playerCard=0, djmap={"1"="2", 2=3, 4=5}, loginState=0]":1}
	 */
	public static String getJsonByString(String str,String type) {
		if("Player".equals(type)) {
//			System.out.println("------------------------------");
			String s = str.substring(str.indexOf("[")+1,str.indexOf("]"));
//			System.out.println(s);
			String[] ks = s.split("\\{");
			String[] kks = ks[1].split("\\}");
			String skt ="";
//			System.out.println("---------------------");
//			System.out.println("kks[0]="+kks[0]);
			if(!kks[0].equals("")) {
				StringBuilder sb = new StringBuilder();
				s = sb.append(ks[0]).append("{}").append(kks[1]).toString();
				skt = getMapJsonByString(kks[0]);
			}
			String[] ss = s.split("\\,");
			StringBuilder sb = new StringBuilder() ;
			sb.append("{"+'\"'+type+'\"'+":{");
			int t=0;
			String stt="";
			for(int i=0;i<ss.length;i++) {
				System.out.println(ss[i]);
				String[] sss = ss[i].split("=");
				for(int j=0;j<sss.length;j++) {
					if(j==0) {
						stt='\"'+sss[j].trim()+'\"';
				 	}else if(j==1) {
						if(" djmap".equals(sss[0])) {
							System.out.println("sss="+sss[0].trim());
							if("".equals(skt)) {
								stt = sss[1].trim();
							}else {
								stt=skt;
							}
						}else {
							stt='\"'+sss[1].trim()+'\"';
						}
					}
					sb.append(stt);
					if(j==0) {
						sb.append(":");
						if(sss.length==1) {
							sb.append("\" \",");
						}
					}else if(j==1&&i!=ss.length-1) {
						sb.append(",");
					}
				}
			}
			sb.append("}}");
			return sb.toString();
		}else {
			
			return null;
		}
	}
	
	/**
	 * {1=2, 2=3, 4=5}
	 * @param str
	 * @return
	 */
	public static String getMapJsonByString(String str) {
		String s = str;//.substring(1,str.indexOf("}"));
		if(!"".equals(s)) {
			StringBuilder sb = new StringBuilder();
			sb.append("{");
			String[] ss = s.split(",");
			for(int k=0;k<ss.length;k++) {
				String[] sss = ss[k].split("=");
				for(int z=0;z<sss.length;z++) {
					sss[z] = '\"'+sss[z].trim()+'\"';
					sb.append(sss[z]);
					if(z==0) {
						sb.append(':');
					}else if(z==1&&k!=ss.length-1) {
						sb.append(',');
					}
				}
			}
			sb.append('}');
//			System.out.println(sb.toString());
			return sb.toString();
		}else {
//			return '{'+'}';
			return null;
		}
	}
	
	/**
	 * 获得类名
	 * @param obj
	 * @return
	 */
	public static String getClassName(Object obj) {
		String[] s = obj.getClass().getName().split("\\.");
		return s[s.length-1];
	}
	
	/**
	 * 获得父类名
	 * @param obj
	 * @return
	 */
	public static String getSuperClassName(Object obj) {
		String[] s = obj.getClass().getSuperclass().getName().split("\\.");
		return s[s.length-1];
	}
	
	@Test
	public void test() {
		Room room = new DoubleRoom(RoomTools.createRoomID(2, GameConfig.doubleCommonGame));
		Player player =  new Player();
		player.setPlayerName("admin");
		player.setPassword("admin");
		player.djmap.put("1",2);
		player.djmap.put("2",3);
		player.djmap.put("4",5);		
		room.playermap.put(player,1);
//		String str= getString(player);
//		System.out.println(str);
		String str = JsonTools.getString("{\"superName\":\"Room\",\"data\":{\"playermap\":{\"Player [clientId=null, playerNo=11, playerName=, password=1, playerCard=0, djmap=null, loginState=0, gamestate=1]\":1},\"roomInfo\":{\"createRoomTime\":\"2017-11-10 06:02:48\",\"endOfLoadingGame\":0,\"roomCreateTime\":\"\",\"roomId\":\"2009\",\"roomPLevel\":1,\"roomPeopleCount\":1,\"roomState\":1,\"roomType\":\"2:0\"},\"roomRule\":{\"roomCostCardCount\":0,\"roomXXRule\":0}},\"className\":\"DoubleRoom\"}clientId=null, playerNo=11, playerName=, password=1, playerCard=0, djmap=null, loginState=0, gamestate=1");
		System.out.println("str="+str);
		Room room1 = (Room) JsonTools.parseJson(str);
		System.out.println(room1.toString());
//		System.out.println(JsonTools.getJsonByString("[playerNo=0, playerName=admin, password=admin, playerCard=0, djmap={}, loginState=0]","Player"));
//		System.out.println(getMapJsonByString("{1=2,2=3,4=5}"));
//		String str = "{\"hello\":{\"hello1\":\"test\"},\"hello3\":{\"helloe4\":\"value\"},\"hello2\":\"test2\"}";
//		pasreObjectData(str);
//		findStrLastIndex(str,"}");
	}
	
}

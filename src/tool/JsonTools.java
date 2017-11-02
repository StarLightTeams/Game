package tool;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import config.entity.Log;
import entity.info.Info;
import entity.player.Player;
import net.sf.json.JSONObject;

/**
 * json工具类
 * @author Administrator
 *
 */
public class JsonTools {
	
	/**
	 * 只能解析格式为{"key":"value","key1":"value1","key2":"value2",...}
	 * @param str
	 * @return
	 */
	public static Map<String,String> parseData(String str){
		String[] strs = str.split(",");
		Map<String,String> maps = new HashMap<String, String>();
		for(int i=0;i<strs.length;i++) {
			String[] sstrs = strs[i].split("\"");
			maps.put(sstrs[1], sstrs[3]);
		}
		return maps;
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
	
	public static String  getString(Object obj) {
		JSONObject jObject = new JSONObject();
		jObject.put("className",getClassName(obj));
		jObject.put("data", obj);
		return jObject.toString();
	}
	
	public static Object parseJson(String str) {
		JSONObject jObject = JSONObject.fromObject(str);
		String className = jObject.getString("className");
		if("Player".equals(className)) {
			Player player = new Player();
			player.setPlayerName(jObject.getJSONObject("data").getString("playerName"));
			player.setPassword(jObject.getJSONObject("data").getString("password"));
			player.setLoginState(Integer.parseInt(jObject.getJSONObject("data").getString("loginState")));
			player.setClientId(jObject.getJSONObject("data").getString("clientId"));
			return player;
		}else if("Info".equals(className)){
			Info info = new Info();
			info.setHeadInfo(jObject.getJSONObject("data").getString("headInfo"));
			info.setDataInfo(jObject.getJSONObject("data").getString("dataInfo"));
			return info;
		}else {
			
			return null;
		}
		
	}
	
	public static String getClassName(Object obj) {
		String[] s = obj.getClass().getName().split("\\.");
		return s[s.length-1];
	}
	
	@Test
	public void test() {
//		Player player =  new Player();
//		player.setPlayerName("admin");
//		player.setPassword("admin");
//		String str= getString(player);
//		System.out.println(str);
//		
//		Player player1 = (Player) parseJson(str);
//		System.out.println(player1.toString());
		String str="{'gameType':'1','clientId':'127.0.0.1:10020','roomKey':'1'}";
		Map<String,String> maps = parseData(str);
		CommonTools.listMaps(maps);
	}
}

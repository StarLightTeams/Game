package tool;

import org.junit.Test;

import entity.info.Info;
import entity.player.Player;
import net.sf.json.JSONObject;

/**
 * jsonπ§æﬂ¿‡
 * @author Administrator
 *
 */
public class JsonTools {
	
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
		Player player =  new Player();
		player.setPlayerName("admin");
		player.setPassword("admin");
		String str= getString(player);
		System.out.println(str);
		
		Player player1 = (Player) parseJson(str);
		System.out.println(player1.toString());
	}
}

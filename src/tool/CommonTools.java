package tool;

import java.util.Iterator;
import java.util.Map;

import config.entity.Log;

/**
 * ͨ�ù���
 */
public class CommonTools {
	/**
	 * ����map
	 */
	public static void listMaps(Map map) {
		Iterator entries = map.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry entry = (Map.Entry) entries.next();
			Log.d("key="+entry.getKey()+",value="+entry.getValue());
		}
	}
}

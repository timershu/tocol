package tocol.rpc.protocol;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实例化后的协议管理类
 * @author Administrator
 *
 */
public class ProtocolManager {

	private ProtocolManager(){}
	
	private static class ProtocolSingle{
		private static Map<String, Protocol> proMap=new ConcurrentHashMap<String, Protocol>();
		
		protected static void put(String key,Protocol value){
			proMap.put(key, value);
		}
		
		protected static Protocol get(String key){
			return proMap.get(key);
		}
		
		protected static Map<String, Protocol> getMaps(){
			return proMap;
		}
	}
	
	public static void put(String key,Protocol value){
		ProtocolSingle.put(key, value);
	}
	
	public static Protocol get(String key){
		return ProtocolSingle.get(key);
	}
	
	public static Map<String, Protocol> getMaps(){
		return ProtocolSingle.getMaps();
	}
}

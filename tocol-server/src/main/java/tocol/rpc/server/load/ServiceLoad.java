package tocol.rpc.server.load;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import tocol.rpc.common.BeanTocol;
import tocol.rpc.common.conf.ConfProperties;
import tocol.rpc.common.conf.Constants;
import tocol.rpc.common.conf.Services;
import tocol.rpc.server.Server;
import tocol.rpc.server.mina.MinaServer;
import tocol.rpc.server.netty.NettyServer;

/**
 * 
 * @author Administrator
 * 
 */
public class ServiceLoad {

	private final static Map<String, Method> methodMap = new ConcurrentHashMap<String, Method>();

	private final static Map<String, Object> serviceImplMap = new ConcurrentHashMap<>();

	private static BeanTocol beanTocol;
	
	private static Boolean start=false;
	

	public static void init(Services services) {
		 List<Integer> port=services.getPort();
		 for(Integer i:port){
			 start(services,i);
			 initService(services,i);
		 }
		
	}
	
	private static void start(Services services,int port){
		String serverName=services.getServerName();
		if(Constants.nettyServer.equals(serverName)){
			Server server = new NettyServer(port);
			server.start();
		}else if(Constants.minaServer.equals(serverName)){
			Server server = new MinaServer(port);
			server.start();
		}
	}
	
	private static void initService(Services services,int port) {
		
		Map<String, String> maps = services.getServiceMaps();
		for (String key : maps.keySet()) {
			try {
				Object value =beanTocol.getBean(maps.get(key));
				serviceImplMap.put(key, value);
				Method[] ms = Class.forName(key).getMethods();
				for (int i = 0; i < ms.length; i++) {
					methodMap.put(key + ms[i].getName(), ms[i]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static Object getServiceImplMap(String serviceName) {
		return serviceImplMap.get(serviceName);
	}

	public static Method getMethodsmap(String key) {
		return methodMap.get(key);
	}

	public static Method getMethodsmap(String key, String methodName) {
		return getMethodsmap(key + methodName);
	}

	public static void init(BeanTocol beanTocol) {
		synchronized (start) {  //服务端启动
			if(!start){
				ServiceLoad.beanTocol=beanTocol;
				ServiceLoad.init(ConfProperties.getServices());
				start=true;
			}
		}
	}

}

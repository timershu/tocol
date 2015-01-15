package tocol.rpc.server.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tocol.rpc.common.channel.ChannelManager;
import tocol.rpc.common.conf.ConfProperties;
import tocol.rpc.protocol.params.Constants;
import tocol.rpc.server.load.Services;

public class ServerConfProperties extends ConfProperties {


	private static Services services = null;
	
	private static Logger logger=LoggerFactory.getLogger(ServerConfProperties.class);

	static {
		init();
	}

	

	public static Services getServices() {
		return services;
	}

	public static void init() {
		Map<String, Properties> mapProperties=getMapProperties();
		for(String key:mapProperties.keySet()){
			if (Constants.serverConfName.equals(key)) {
				initServer(mapProperties.get(key));
			}
		}
	}
	
	/**
	 * 
	 * @param io
	 */
	private static void initServer(Properties p) {
		try {
			services = new Services();
			Map<String, String> maps = new HashMap<>();
			List<Integer> portList=new ArrayList<>();
			for (String key : p.stringPropertyNames()) {
				if (key.startsWith(Constants.serverServicePrefix) && key.endsWith(Constants.serverServiceSuffix)) {
					Object value = p.get(key);
					String[] values = value.toString().split(":");
					maps.put(values[0], values[1]);
				}else if(key.startsWith(Constants.serverPortPrefix) && key.endsWith(Constants.serverPortSuffix)){
					Object value = p.get(key);
					portList.add(Integer.valueOf(value.toString()));
				}
			}
			Object o=p.get(Constants.serverName);
			if(o==null){
				o=Constants.nettyServer;
			}
			services.setServerName(o.toString());
			services.setServiceMaps(maps);
			services.setPort(portList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 static {
	        Runtime.getRuntime().addShutdownHook(new Thread(){
	            @Override
	            public void run() {
	            	logger.info("JVM is shutting down... Stop");
	            	Map<String, List<ChannelManager>> maps=ChannelManagerServerSingle.getChannelManagerMap();
	            	for(String key:maps.keySet()){
	            		try {
							ChannelManagerServerSingle.close(key);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	}
	            }
	        });
	    }
}

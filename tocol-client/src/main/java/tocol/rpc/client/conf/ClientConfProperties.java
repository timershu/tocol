package tocol.rpc.client.conf;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tocol.rpc.client.heart.HeartClient;
import tocol.rpc.client.load.Hosts;
import tocol.rpc.common.channel.ChannelManager;
import tocol.rpc.common.conf.ConfProperties;
import tocol.rpc.protocol.params.Constants;

public class ClientConfProperties extends ConfProperties {
	private static List<Hosts> hostsList = null;

	private static Logger logger=LoggerFactory.getLogger(ClientConfProperties.class);

	static {
		init();
	}

	public static List<Hosts> getHostsList() {
		return hostsList;
	}

	

	public static void init() {
		Map<String, Properties> mapProperties=getMapProperties();
		for(String key:mapProperties.keySet()){
			if (Constants.clientConfName.equals(key)) {
				initClient(mapProperties.get(key));
			}
		}
		

	}
	
	

	/**
	 * 
	 * @param io
	 */
	private static void initClient(Properties p) {
		try {
			hostsList = new ArrayList<>();
			Object o=p.get(Constants.serverName);
			if(o==null){
				o=Constants.nettyServer;
			}
			for (String key : p.stringPropertyNames()) {
				if (key.startsWith(Constants.clientServerPrefix) && key.endsWith(Constants.clientServerSuffix)) {
					Object value = p.get(key);
					Hosts host = null;
					String[] values = value.toString().split(":");
					if (values.length == 1) {
						host = new Hosts(values[0],80);
						host.setConnectionCount(1);
					} else if (values.length == 2){
						host = new Hosts(values[0],Integer.parseInt(values[1]));
						host.setConnectionCount(1);
					}else{
						host = new Hosts(values[0],Integer.parseInt(values[1]));
						host.setConnectionCount(Integer.parseInt(values[2]));
					}
					host.setServerName(o.toString());
					hostsList.add(host);
				}
			}
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
	            	HeartClient.stop();
	            	Map<String, List<ChannelManager>> maps=ChannelManagerClientSingle.getChannelManagerMap();
	            	for(String key:maps.keySet()){
	            		ChannelManagerClientSingle.close(key);
	            	}
	            }
	        });
	    }
}

package tocol.rpc.common.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import tocol.rpc.protocol.Protocol;
import tocol.rpc.protocol.ProtocolManager;
import tocol.rpc.protocol.params.Constants;

public abstract class ConfProperties {

	private static List<Hosts> hostsList = null;

	private static Services services = null;

	static {
		init();
	}

	public static List<Hosts> getHostsList() {
		return hostsList;
	}

	public static Services getServices() {
		return services;
	}

	public static void init() {

		File file = new File(ConfProperties.class.getClassLoader().getResource("").getPath());
		File[] listFiles = file.listFiles();
		InputStream io = null;
		for (int i = 0; i < listFiles.length; i++) {
			
			if(listFiles[i].isDirectory()){
				if(Constants.protocolDirectory.equals(listFiles[i].getName())){
					File[] fs=listFiles[i].listFiles();
					for(File f:fs){
						try {
							io = new FileInputStream(f);
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						initProtocol(io);
					}
				}
				
			}
			
			if (Constants.clientConfName.equals(listFiles[i].getName())) {
				try {
					io = new FileInputStream(listFiles[i]);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				initClient(io);
			} else if (Constants.serverConfName.equals(listFiles[i].getName())) {
				try {
					io = new FileInputStream(listFiles[i]);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				initServer(io);
			}
		}

	}
	
	private static void initProtocol(InputStream io){
		Properties p = new Properties();
		try {
			p.load(io);
			for (String key : p.stringPropertyNames()) {
				String value=p.getProperty(key);
				Protocol o=(Protocol) Class.forName(value).newInstance();
				ProtocolManager.put(key, o);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param io
	 */
	private static void initClient(InputStream io) {
		Properties p = new Properties();
		try {
			p.load(io);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param io
	 */
	private static void initServer(InputStream io) {
		Properties p = new Properties();
		try {
			p.load(io);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(services);
		System.out.println(hostsList);
	}
}

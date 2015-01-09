package tocol.rpc.common.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import tocol.rpc.protocol.Protocol;
import tocol.rpc.protocol.ProtocolManager;
import tocol.rpc.protocol.params.Constants;

public abstract class ConfProperties {

	public static Map<String, Properties> getMapProperties() {
		return mapProperties;
	}

	private static Map<String, Properties> mapProperties=new ConcurrentHashMap<String, Properties>();

	static {
		init();
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
				initClient(Constants.clientConfName,io);
			} else if (Constants.serverConfName.equals(listFiles[i].getName())) {
				try {
					io = new FileInputStream(listFiles[i]);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				initServer(Constants.serverConfName,io);
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
	private static void initClient(String key,InputStream io) {
		Properties p = new Properties();
		try {
			p.load(io);
			mapProperties.put(key, p);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param io
	 */
	private static void initServer(String key,InputStream io) {
		Properties p = new Properties();
		try {
			p.load(io);
			mapProperties.put(key, p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

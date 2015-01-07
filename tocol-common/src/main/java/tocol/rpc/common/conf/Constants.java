package tocol.rpc.common.conf;

public abstract class Constants {
	
	

	public static final String projectName="tocol";
	
	public static final String nettyServer = "netty";
	
	public static final String minaServer = "mina";

	public static final String clientServerPrefix =projectName+ ".server";

	public static final String clientServerSuffix = ".host";

	public static final String serverServicePrefix =projectName+ ".service";

	public static final String serverServiceSuffix = ".name";
	
	public static final String serverPortPrefix = projectName+".server";
	
	public static final String serverName=projectName+".server.name";

	public static final String serverPortSuffix = "port";

	public static final String clientConfName = "client.properties";

	public static final String serverConfName = "server.properties";
	
	
	public static final String protocolDirectory="tocolProtocol";

	public static final int DEFAULT_TIMEOUT = 2000;//

	public static final String version = "1.0";

	public static final String MessageTypeHeartbeat = "Heartbeat";

	public static final String MessageTypeService="service";

	
	public static final String HESSIAN = "hessian";

	public static final String JAVA = "java";
	
	public static int TimeOutCount=3;//重试次数
	
	public static final String StatusError="error";
	
	public static final String StatusSuccess="success";
	

}

package tocol.rpc.client.load;

import java.util.List;

import tocol.rpc.client.Client;
import tocol.rpc.client.conf.ClientConfProperties;
import tocol.rpc.client.heart.HeartClient;
import tocol.rpc.client.mina.MinaClient;
import tocol.rpc.client.netty.NettyClient;
import tocol.rpc.protocol.params.Constants;

/**
 * 连接初始化
 * 
 * @author Administrator
 * 
 */
public class ServerLoad {

	static {
		ServerLoad.init(ClientConfProperties.getHostsList());
		HeartClient.main(null);
	}

	public static void init(List<Hosts> hostsList) {
		for (int i = 0; i < hostsList.size(); i++) {
			try {
				Hosts host = hostsList.get(i);
				for(int j=0;j<host.getConnectionCount();j++){
					connection(host);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Client connection(Hosts host)throws Exception{
		Client client = null;
		try {
			String serverName=host.getServerName();
			if(Constants.nettyServer.equals(serverName)){
				client = new NettyClient(host);
				client.connect();
			}else if(Constants.minaServer.equals(serverName)){
				client = new MinaClient(host);
				client.connect();
			}
			
			return client;
		} catch (Exception e) {
			throw e;
		}
	}

	public static void main(String[] args) {

	}
}

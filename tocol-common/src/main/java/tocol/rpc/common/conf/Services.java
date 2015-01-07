package tocol.rpc.common.conf;

import java.util.List;
import java.util.Map;

public class Services {

	private Map<String, String> serviceMaps;

	private List<Integer> port;

	private int timeout;

	private String serverName;
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Map<String, String> getServiceMaps() {
		return serviceMaps;
	}

	public void setServiceMaps(Map<String, String> serviceMaps) {
		this.serviceMaps = serviceMaps;
	}

	

	public List<Integer> getPort() {
		return port;
	}

	public void setPort(List<Integer> port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

}

package tocol.rpc.protocol.params;

import java.io.Serializable;

/**
 * 
 * @author Administrator
 * 
 */
public abstract class Params implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;//
	private String serviceName;//
	private String version = Constants.version;//
	private String protocol = Constants.HESSIAN;//
	private String messageType=Constants.MessageTypeService;
	

	private String host;//
	private int port;//
	private transient boolean keepalive = true;//
	private transient boolean heartbeat;//
	private transient int heartbeatTime;//
	private long id;
	private transient String message;
	private transient String status;
	
	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isHeartbeat() {
		return heartbeat;
	}

	public void setHeartbeat(boolean heartbeat) {
		this.heartbeat = heartbeat;
	}

	public int getHeartbeatTime() {
		return heartbeatTime;
	}

	public void setHeartbeatTime(int heartbeatTime) {
		this.heartbeatTime = heartbeatTime;
	}

	public boolean isKeepalive() {
		return keepalive;
	}

	public void setKeepalive(boolean keepalive) {
		this.keepalive = keepalive;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

}

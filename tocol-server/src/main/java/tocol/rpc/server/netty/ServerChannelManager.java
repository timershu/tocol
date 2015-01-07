package tocol.rpc.server.netty;

import io.netty.channel.Channel;
import tocol.rpc.common.channel.ChannelManager;
import tocol.rpc.server.Server;

public class ServerChannelManager extends ChannelManager {

	private final Server server;

	public ServerChannelManager(Server server,Channel channel, String hostName) {
		super(channel,hostName);
		this.server = server;
	}

	public Server getServer() {
		return server;
	}

}

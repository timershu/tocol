package tocol.rpc.client.netty;

import io.netty.channel.Channel;
import tocol.rpc.client.Client;
import tocol.rpc.common.channel.ChannelManager;

public class ClientChannelManager extends ChannelManager {

	public Client getClient() {
		return client;
	}

	private final Client client;

	public ClientChannelManager(Channel channel, Client client,String hostName) {
		super(channel,hostName);
		this.client = client;
		// TODO Auto-generated constructor stub
	}

}

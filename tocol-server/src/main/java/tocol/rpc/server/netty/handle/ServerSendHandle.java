package tocol.rpc.server.netty.handle;

import io.netty.channel.Channel;
import tocol.rpc.protocol.handle.AbstractSendHandle;

public class ServerSendHandle extends AbstractSendHandle<Channel> {

	@Override
	public void sendObject(Channel channel, Object obj) {
		// TODO Auto-generated method stub
		channel.writeAndFlush(obj);
	}

}

package tocol.rpc.server.netty.handle;

import io.netty.channel.Channel;
import tocol.rpc.common.handle.AbstractSendHandle;

public class ServerSendHandle extends AbstractSendHandle {

	@Override
	public void sendObject(Channel channel, Object obj) {
		// TODO Auto-generated method stub
		channel.writeAndFlush(obj);
	}

}

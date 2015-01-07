package tocol.rpc.common.handle;

import io.netty.channel.Channel;

public abstract class AbstractSendHandle implements SendHandle {

	@Override
	public void send(Channel channel, Object obj) {
		// TODO Auto-generated method stub
		sendObject(channel, obj);
	}

	public abstract void sendObject(Channel channel, Object obj);

}

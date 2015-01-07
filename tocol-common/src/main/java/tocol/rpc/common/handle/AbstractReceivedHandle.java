package tocol.rpc.common.handle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import tocol.rpc.protocol.Protocol;

public abstract class AbstractReceivedHandle implements ReceivedHandle {

	protected final Protocol protocol;

	public AbstractReceivedHandle(Protocol protocol) {
		super();
		this.protocol = protocol;
	}

	public abstract void receivedObject(Channel channel, Object obj);

	@Override
	public Object received(Channel channel, Object obj) {
		Object o = null;
		if (obj instanceof ByteBuf) {
			try {
				o = protocol.decoder((ByteBuf) obj);
				receivedObject(channel, o);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return o;
	}

}

package tocol.rpc.protocol.handle;

import io.netty.buffer.ByteBuf;
import tocol.rpc.protocol.Protocol;

public abstract class AbstractReceivedHandle<T> implements ReceivedHandle<T> {

	protected final Protocol protocol;

	public AbstractReceivedHandle(Protocol protocol) {
		super();
		this.protocol = protocol;
	}

	public abstract void receivedObject(T channel, Object obj);

	@Override
	public Object received(T channel, Object obj) {
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

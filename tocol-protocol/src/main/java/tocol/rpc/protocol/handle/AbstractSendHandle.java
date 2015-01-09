package tocol.rpc.protocol.handle;


public abstract class AbstractSendHandle<T> implements SendHandle<T> {

	@Override
	public void send(T channel, Object obj) {
		// TODO Auto-generated method stub
		sendObject(channel, obj);
	}

	public abstract void sendObject(T channel, Object obj);

}

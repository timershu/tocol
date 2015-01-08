package tocol.rpc.protocol;


public abstract class AbstractProtocol<T> implements Protocol<T> {

	public abstract Protocol<T> getProtocol(Object arg1) throws Exception;

	
	public void encoder(T arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		Protocol<T> p = getProtocol(arg1);
		p.encoder(arg0, arg1);
	}

	
	public Object decoder(T arg0) throws Exception {
		// TODO Auto-generated method stub
		Protocol<T> p = getProtocol(arg0);
		return p.decoder(arg0);
	}

}

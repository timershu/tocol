package tocol.rpc.protocol;

import io.netty.buffer.ByteBuf;
import tocol.rpc.protocol.Protocol;

public abstract class AbstractProtocol implements Protocol {

	public abstract Protocol getProtocol(Object arg1) throws Exception;

	
	public void encoder(ByteBuf arg0, Object arg1) throws Exception {
		// TODO Auto-generated method stub
		Protocol p = getProtocol(arg1);
		p.encoder(arg0, arg1);
	}

	
	public Object decoder(ByteBuf arg0) throws Exception {
		// TODO Auto-generated method stub
		Protocol p = getProtocol(arg0);
		return p.decoder(arg0);
	}

}

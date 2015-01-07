package tocol.rpc.protocol;

import io.netty.buffer.ByteBuf;

/**
 * Protocol Interface
 * 
 * @author Administrator
 * 
 */
public interface Protocol {

	/**
	 * 
	 * @param arg0
	 * @param arg1
	 * @throws Exception
	 */
	public void encoder(ByteBuf arg0, Object arg1) throws Exception;

	/**
	 * 
	 * @param arg0
	 * @param arg1
	 * @return
	 * @throws Exception
	 */
	public Object decoder(ByteBuf arg0) throws Exception;
}

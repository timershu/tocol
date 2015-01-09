package tocol.rpc.protocol.handle;

import io.netty.channel.Channel;

/**
 * 接收数据
 * 
 * @author Administrator
 * 
 */
public interface ReceivedHandle<T> {

	/**
	 * 接收数据
	 * 
	 * @param channel
	 * @param obj
	 */
	public Object received(T channel, Object obj);

}

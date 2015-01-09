package tocol.rpc.protocol.handle;

import io.netty.channel.Channel;

/**
 * 发送数据
 * 
 * @author Administrator
 * 
 */
public interface SendHandle<T> {

	/**
	 * 发送数据
	 * 
	 * @param channel
	 * @param obj
	 */
	public void send(T channel, Object obj);

}

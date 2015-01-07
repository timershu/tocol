package tocol.rpc.common.handle;

import io.netty.channel.Channel;

/**
 * 接收数据
 * 
 * @author Administrator
 * 
 */
public interface ReceivedHandle {

	/**
	 * 接收数据
	 * 
	 * @param channel
	 * @param obj
	 */
	public Object received(Channel channel, Object obj);

}

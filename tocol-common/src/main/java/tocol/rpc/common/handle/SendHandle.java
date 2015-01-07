package tocol.rpc.common.handle;

import io.netty.channel.Channel;

/**
 * 发送数据
 * 
 * @author Administrator
 * 
 */
public interface SendHandle {

	/**
	 * 发送数据
	 * 
	 * @param channel
	 * @param obj
	 */
	public void send(Channel channel, Object obj);

}

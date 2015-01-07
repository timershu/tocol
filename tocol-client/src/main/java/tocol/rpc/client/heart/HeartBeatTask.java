package tocol.rpc.client.heart;

import java.util.List;

import tocol.rpc.client.conf.ChannelManagerClientSingle;
import tocol.rpc.common.channel.ChannelManager;
import tocol.rpc.common.conf.Constants;
import tocol.rpc.protocol.params.RequestParams;

/**
 * 心跳任务
 * 
 * @author Administrator
 * 
 */
public class HeartBeatTask implements Runnable {

	private long timeout;

	public HeartBeatTask(long timeout) {
		super();
		this.timeout = timeout * 1000;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (String key : ChannelManagerClientSingle.getChannelManagerMap().keySet()) {
			try {
				List<ChannelManager> lists = ChannelManagerClientSingle.getChannelManagerMap().get(key);
				for(ChannelManager l:lists){
					heart(l);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	
	private void heart(ChannelManager channelManager){
		try {
			if (channelManager.getChannel() == null || !channelManager.getChannel().isActive()) {
				ChannelManagerClientSingle.remove(channelManager.getHostName(),channelManager.getChannel());
				return;
			}

			if ((System.currentTimeMillis() - channelManager.getTransferTime()) > timeout) {
				RequestParams request = new RequestParams();
				request.setMessageType(Constants.MessageTypeHeartbeat);
				request.setVersion(Constants.version);
				channelManager.getChannel().writeAndFlush(request);
			}
		} catch (Exception e) {
			System.out.println("心跳异常:"+e);
		}
	}

}

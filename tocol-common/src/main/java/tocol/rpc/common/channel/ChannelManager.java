package tocol.rpc.common.channel;

import io.netty.channel.Channel;

public abstract class ChannelManager implements Comparable<ChannelManager> {

	public String getHostName() {
		return hostName;
	}

	private final Channel channel;

	private final String hostName;
	
	private long transferTime = 0;
	
	private int connectCount;//连接数

	public ChannelManager(Channel channel,String hostName) {
		this.channel = channel;
		this.hostName=hostName;
	}

	public long getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(long transferTime) {
		this.transferTime = transferTime;
	}

	public Channel getChannel() {
		return channel;
	}
	
	
	public int compareTo(ChannelManager o) {
		// TODO Auto-generated method stub
		return (int) (o.getTransferTime()-this.transferTime);
	}

	public int getConnectCount() {
		return connectCount;
	}

	public void setConnectCount(int connectCount) {
		this.connectCount = connectCount;
	}

}

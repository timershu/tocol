package tocol.rpc.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

import tocol.rpc.client.Client;
import tocol.rpc.client.conf.ChannelManagerClientSingle;
import tocol.rpc.client.netty.handle.ClientReceivedHandle;
import tocol.rpc.common.channel.ChannelManager;
import tocol.rpc.common.conf.Constants;
import tocol.rpc.common.conf.Hosts;
import tocol.rpc.common.handle.ReceivedHandle;
import tocol.rpc.protocol.Protocol;
import tocol.rpc.protocol.ProtocolCommon;

public class NettyClient implements Client {

    private final Hosts host;
	private String hostName;
	private ReceivedHandle receivedHandle = null;
	private EventLoopGroup group = null;
	private Bootstrap b = null;
	private Channel nowChannel = null;
	private Protocol protocol;

	public NettyClient(Hosts host) {
		super();
		this.host = host;
		this.hostName = host.getHost() + ":" + host.getPort();
		protocol = new ProtocolCommon();
		receivedHandle = new ClientReceivedHandle(protocol);
		group = new NioEventLoopGroup();

		b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
				.handler(new ClientInitializer(hostName, receivedHandle,protocol));
	}

	@Override
	public void connect() {
		try {
			System.out.println("开始连接");
			ChannelFuture future = b.connect(host.getHost(), host.getPort());
			boolean ret = future.awaitUninterruptibly(Constants.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
			if (ret && future.isSuccess()) {
				Channel newChannel = future.channel();
				nowChannel = newChannel;
				ChannelManager channelManager = new ClientChannelManager(newChannel, this,hostName);
				channelManager.setConnectCount(host.getConnectionCount());
				ChannelManagerClientSingle.put(hostName, channelManager);
				System.out.println("新连接:"+nowChannel+"\t"+ChannelManagerClientSingle.getChannelManagerMap().get(hostName).size());
			}
		} catch (Exception e) {
			System.out.println("连接失败："+e);
		}
	}

	@Override
	public void doConnect() {
		// TODO Auto-generated method stub
		stop();
		connect();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		System.out.println("开始断开");
		if(nowChannel!=null){
			nowChannel.close();
			nowChannel=null;
		}
		
	}

}

package tocol.rpc.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tocol.rpc.common.channel.ChannelManager;
import tocol.rpc.protocol.NettyProtocol;
import tocol.rpc.protocol.Protocol;
import tocol.rpc.protocol.handle.ReceivedHandle;
import tocol.rpc.protocol.handle.SendHandle;
import tocol.rpc.server.AbstractServer;
import tocol.rpc.server.Server;
import tocol.rpc.server.conf.ChannelManagerServerSingle;
import tocol.rpc.server.netty.handle.ServerReceivedHandle;
import tocol.rpc.server.netty.handle.ServerSendHandle;

public class NettyServer extends AbstractServer{
	
	private static Logger log=LoggerFactory.getLogger(NettyServer.class);
	private int port = 8081;
	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;
	private ServerBootstrap b = null;
	private SendHandle<Channel> sendHandle;
	private Protocol<ByteBuf> protocol;

	private String hostName;
	public NettyServer(int port) {
		super();
		this.port = port;
		this.hostName=String.valueOf(port);
	}

	private ReceivedHandle<Channel> receivedHandle;

	public static void main(String[] args) {
		int PORT = 8081;
		Server server = new NettyServer(PORT);
		server.start();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		sendHandle = new ServerSendHandle();
		protocol = new NettyProtocol();
		receivedHandle = new ServerReceivedHandle(sendHandle, protocol);
		bossGroup = new NioEventLoopGroup(1);
		workerGroup = new NioEventLoopGroup();
		try {
			b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.handler(new LoggingHandler(LogLevel.INFO))
					.childHandler(
							new ServerChannelPipelineFactory(this,receivedHandle,protocol,hostName));
			b.bind(port);
			log.info("Start Server Port "+port);
		} catch(Exception e){
			log.error("Server Error", e);
		}finally {

		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		Map<String, List<ChannelManager>> maps=ChannelManagerServerSingle.getChannelManagerMap();
		for(String key:maps.keySet()){
			try {
				ChannelManagerServerSingle.close(key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

}

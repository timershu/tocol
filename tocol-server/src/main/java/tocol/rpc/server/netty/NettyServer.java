package tocol.rpc.server.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import tocol.rpc.common.handle.ReceivedHandle;
import tocol.rpc.common.handle.SendHandle;
import tocol.rpc.protocol.Protocol;
import tocol.rpc.protocol.ProtocolCommon;
import tocol.rpc.server.Server;
import tocol.rpc.server.netty.handle.ServerReceivedHandle;
import tocol.rpc.server.netty.handle.ServerSendHandle;

public class NettyServer implements Server {
	
	private static Logger log=LoggerFactory.getLogger(NettyServer.class);
	private int port = 8081;
	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;
	private ServerBootstrap b = null;
	private SendHandle sendHandle;
	private Protocol protocol;

	private String hostName;
	public NettyServer(int port) {
		super();
		this.port = port;
		this.hostName=String.valueOf(port);
	}

	private ReceivedHandle receivedHandle;

	public static void main(String[] args) {
		int PORT = 8081;
		Server server = new NettyServer(PORT);
		server.start();
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		sendHandle = new ServerSendHandle();
		protocol = new ProtocolCommon();
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
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

}

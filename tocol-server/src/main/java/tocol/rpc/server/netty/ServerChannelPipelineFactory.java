package tocol.rpc.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import tocol.rpc.common.handle.ReceivedHandle;
import tocol.rpc.protocol.Protocol;
import tocol.rpc.server.Server;

public class ServerChannelPipelineFactory extends
		ChannelInitializer<SocketChannel> {
	private final ReceivedHandle receivedHandle;
	private final Protocol protocol;
    private final Server server;
	private final String hostName;
	public ServerChannelPipelineFactory(Server server,ReceivedHandle receivedHandle,Protocol protocol,String hostName) {
		super();
		this.server=server;
		this.receivedHandle = receivedHandle;
		this.protocol = protocol;
		this.hostName=hostName;
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		pipeline.addLast(new LengthFieldPrepender(4));// 4个字节存放长度
		pipeline.addLast(new ServerRequestEncoder(protocol));
		pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0,4));
		pipeline.addLast(new IdleStateHandler(30, 0, 0));
		pipeline.addLast(new ServerHandlerAdapter(server,receivedHandle,hostName));
	}

}

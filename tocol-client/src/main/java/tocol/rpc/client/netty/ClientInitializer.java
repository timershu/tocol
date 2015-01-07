package tocol.rpc.client.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import tocol.rpc.common.handle.ReceivedHandle;
import tocol.rpc.protocol.Protocol;

public class ClientInitializer extends ChannelInitializer<SocketChannel> {
	private final String hostName;
	private final ReceivedHandle receivedHandle;
	private final Protocol protocol;

	public ClientInitializer(String hostName, ReceivedHandle receivedHandle,
			Protocol protocol) {
		super();
		this.hostName = hostName;
		this.receivedHandle = receivedHandle;
		this.protocol = protocol;
	}

	@Override
	protected void initChannel(SocketChannel arg0) throws Exception {
		// TODO Auto-generated method stub
		ChannelPipeline p = arg0.pipeline();
		p.addLast(new LengthFieldPrepender(4));
		p.addLast(new ClientRequestEncoder(protocol));
		p.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4));
		p.addLast(new IdleStateHandler(0, 30, 0));
		p.addLast(new ClientHandler(hostName, receivedHandle));
	}

}

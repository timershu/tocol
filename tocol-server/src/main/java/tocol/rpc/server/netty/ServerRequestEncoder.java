package tocol.rpc.server.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import tocol.rpc.protocol.Protocol;
import tocol.rpc.protocol.params.ResponseParams;

public class ServerRequestEncoder extends MessageToByteEncoder<ResponseParams> {

	private final Protocol protocol;

	public ServerRequestEncoder(Protocol protocol) {
		super();
		this.protocol = protocol;
	}

	@Override
	protected void encode(ChannelHandlerContext arg0, ResponseParams arg1,
			ByteBuf arg2) throws Exception {
		// TODO Auto-generated method stub
		protocol.encoder(arg2, arg1);
		arg0.flush();
	}

}

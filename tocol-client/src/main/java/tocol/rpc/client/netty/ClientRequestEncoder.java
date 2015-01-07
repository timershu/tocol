package tocol.rpc.client.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import tocol.rpc.protocol.Protocol;
import tocol.rpc.protocol.params.RequestParams;

public class ClientRequestEncoder extends MessageToByteEncoder<RequestParams> {

	private final Protocol protocol;

	public ClientRequestEncoder(Protocol protocol) {
		super();
		this.protocol = protocol;
	}

	@Override
	protected void encode(ChannelHandlerContext arg0, RequestParams arg1,
			ByteBuf arg2) throws Exception {
		// TODO Auto-generated method stub

		protocol.encoder(arg2, arg1);
		arg0.flush();
	}
}

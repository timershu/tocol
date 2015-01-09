package tocol.rpc.client.netty.handle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import tocol.rpc.client.future.ResponseFuture;
import tocol.rpc.protocol.Protocol;
import tocol.rpc.protocol.handle.AbstractReceivedHandle;
import tocol.rpc.protocol.params.Constants;
import tocol.rpc.protocol.params.ResponseParams;

public class ClientReceivedHandle extends AbstractReceivedHandle<Channel> {

	public ClientReceivedHandle(Protocol<ByteBuf> protocol) {
		super(protocol);
	}

	@Override
	public void receivedObject(Channel channel, Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof ResponseParams) {
			ResponseParams response = (ResponseParams) obj;
			if(Constants.MessageTypeService.equals(response.getMessageType())){
				ResponseFuture.received(response);
			}
		}
	}

}

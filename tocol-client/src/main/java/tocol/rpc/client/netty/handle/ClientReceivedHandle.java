package tocol.rpc.client.netty.handle;

import io.netty.channel.Channel;
import tocol.rpc.client.future.ResponseFuture;
import tocol.rpc.common.conf.Constants;
import tocol.rpc.common.handle.AbstractReceivedHandle;
import tocol.rpc.protocol.Protocol;
import tocol.rpc.protocol.params.ResponseParams;

public class ClientReceivedHandle extends AbstractReceivedHandle {

	public ClientReceivedHandle(Protocol protocol) {
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

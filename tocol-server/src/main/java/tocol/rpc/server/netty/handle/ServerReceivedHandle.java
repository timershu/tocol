package tocol.rpc.server.netty.handle;

import io.netty.channel.Channel;
import tocol.rpc.common.handle.AbstractReceivedHandle;
import tocol.rpc.common.handle.SendHandle;
import tocol.rpc.protocol.Protocol;
import tocol.rpc.protocol.params.Constants;
import tocol.rpc.protocol.params.RequestParams;
import tocol.rpc.protocol.params.ResponseParams;
import tocol.rpc.server.invoker.InvokerResult;

public class ServerReceivedHandle extends AbstractReceivedHandle {

	private final SendHandle sendHandle;

	@Override
	public void receivedObject(Channel channel, Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof RequestParams) {
			RequestParams request = (RequestParams) obj;
			ResponseParams response = new ResponseParams(request.getId());
			//请求服务的包
			if (Constants.MessageTypeService.equals(request.getMessageType())) {
				Object result = InvokerResult.invoke(request);
				response.setValue(result);
			}
			response.setMessageType(request.getMessageType());
			response.setMethod(request.getMethod());
			response.setVersion(request.getVersion());
			response.setServiceName(request.getServiceName());
			response.setProtocol(request.getProtocol());
			sendHandle.send(channel, response);
		}
	}

	public ServerReceivedHandle(SendHandle sendHandle, Protocol protocol) {
		super(protocol);
		this.sendHandle = sendHandle;
	}

}

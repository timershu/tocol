package tocol.rpc.client.factory;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import tocol.rpc.client.conf.ChannelManagerClientSingle;
import tocol.rpc.client.future.ResponseFuture;
import tocol.rpc.common.channel.ChannelManager;
import tocol.rpc.common.conf.Constants;
import tocol.rpc.protocol.params.RequestParams;
import tocol.rpc.protocol.params.ResponseParams;

public class ClientProxy implements InvocationHandler, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String url;
	private final String serviceName;

	public ClientProxy(String url, String serviceName) {
		super();
		this.url = url;
		this.serviceName = serviceName;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		try {
			ChannelManager channel = ChannelManagerClientSingle.get(url);
			RequestParams request = new RequestParams();
			request.setMethod(method.getName());
			request.setValues(args);
			request.setServiceName(serviceName);
			channel.setTransferTime(System.currentTimeMillis());
			System.out.println("channel.getChannel()="+channel.getChannel()+"\trequest="+request);
			channel.getChannel().writeAndFlush(request);
			ResponseFuture response = new ResponseFuture(channel, request,Constants.DEFAULT_TIMEOUT);
			Object result=response.get();
			ResponseParams p=(ResponseParams)result;
			if(Constants.StatusError.equals(p.getStatus())){
				throw new Exception(p.getMessage());
			}
			return p.getValue();
		} catch (Exception e) {
			throw e;
		}
	}

}

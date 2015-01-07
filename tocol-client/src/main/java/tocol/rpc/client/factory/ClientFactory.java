package tocol.rpc.client.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ClientFactory implements Factory {

	private ClassLoader loader = Thread.currentThread().getContextClassLoader();

	@Override
	public Object create(Class api, String url) throws Exception{
		// TODO Auto-generated method stub
		InvocationHandler handler = new ClientProxy(url, api.getName());
		return Proxy.newProxyInstance(loader, new Class[] { api }, handler);
	}

}

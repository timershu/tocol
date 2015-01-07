package tocol.rpc.server.invoker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import tocol.rpc.protocol.params.RequestParams;
import tocol.rpc.server.load.ServiceLoad;

public class InvokerResult {

	public static Object invoke(RequestParams params) {

		String serviceName = params.getServiceName();
		Method m = ServiceLoad.getMethodsmap(serviceName, params.getMethod());
		if (m != null) {
			Object o = ServiceLoad.getServiceImplMap(serviceName);
			Object result = null;
			try {
				result = m.invoke(o, params.getValues());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

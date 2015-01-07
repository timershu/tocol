package tocol.rpc.protocol.params;

import java.util.concurrent.atomic.AtomicLong;

public class RequestParams extends Params {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final AtomicLong INVOKE_ID = new AtomicLong(0);

	private Object[] values;

	public RequestParams() {
		setId(INVOKE_ID.getAndIncrement());
	}

	public Object[] getValues() {
		return values;
	}

	public void setValues(Object[] values) {
		this.values = values;
	}

}

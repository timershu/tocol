package tocol.rpc.protocol.params;

public class ResponseParams extends Params {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Object value;//

	public ResponseParams(long id) {
		setId(id);
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}

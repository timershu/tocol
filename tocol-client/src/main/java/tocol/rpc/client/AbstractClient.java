package tocol.rpc.client;

public abstract class AbstractClient implements Client {

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		stop();
	}

}

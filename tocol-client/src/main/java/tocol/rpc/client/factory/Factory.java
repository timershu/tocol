package tocol.rpc.client.factory;

public interface Factory {

	public Object create(Class api, String url)throws Exception;
}

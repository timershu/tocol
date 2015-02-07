package tocol.rpc.test;
import hessian.service.Hello;
import hessian.service.HelloVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import tocol.rpc.client.conf.ChannelManagerClientSingle;
import tocol.rpc.client.factory.ClientFactory;
import tocol.rpc.client.factory.Factory;
import tocol.rpc.common.channel.ChannelManager;


public class TestClient {
	
	public static AtomicLong successLong=new AtomicLong();
	
	public static AtomicLong errorLong=new AtomicLong();
	

	
	/**
	 * @param args
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext-tocol-client.xml");
		context.start();
		test();
		testThread();
		
		Map<TestClient, String> maps=new HashMap<TestClient, String>();
		for(int i=0;i<100;i++){
			TestClient tc=new TestClient();

			System.out.println(maps.put(tc, i+""));
		}
		System.out.println(maps.size());
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		//System.out.println("obj=="+obj);
		if(obj instanceof TestClient){
			return true;
		}
		return false;
	}
	private static void test() {
		Factory factory = new ClientFactory();
		String url = "127.0.0.1:8081";
		String params = getParams(200);
		System.err.println(params.getBytes().length);
		for (int i = 0; i < 10; i++) {
			try {
				Hello hello = (Hello) factory.create(Hello.class, url);
				HelloVo vo = new HelloVo(params + i, params + i);
				String temp = hello.test(params);// .getHelloVo(vo);
				System.err.println("result is:" + temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static void testThread() throws InterruptedException, ExecutionException {
		long startTime=System.currentTimeMillis();
		ExecutorService service = Executors.newFixedThreadPool(100);
		int count=10;
		int threadsCount=10;
		List<Callable<Long>> tasks=new ArrayList<Callable<Long>>();
		for (int i = 0; i < threadsCount; i++) {
			tasks.add(new Task(count));
			service.submit(new Task(count));
		}
		List<Future<Long>> fs=service.invokeAll(tasks);
		long endTime=System.currentTimeMillis();
		System.out.println("All time is:"+(endTime-startTime));
		for(Future<Long> f:fs){
			System.out.println("avg time is:"+f.get()*1.0/count);
		}
		Map<String, List<ChannelManager>>  maps=ChannelManagerClientSingle.getChannelManagerMap();
		for(String key:maps.keySet()){
			List<ChannelManager> lists=maps.get(key);
			for(ChannelManager l:lists){
				l.getChannel().close();
			}
		}
		System.exit(0);
	}

	public static String getParams(int count) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < count; i++) {
			sb.append(i);
		}
		return sb.toString();
	}
}

class Task implements Callable<Long> {

	private int count;
	
	
	
	public Task(int count) {
		super();
		this.count = count;
	}

	public void run() {
		// TODO Auto-generated method stub
		Factory factory = new ClientFactory();
		String url = "127.0.0.1:8081";
		String params = TestClient.getParams(100);
		for (int i = 0; i < count; i++) {
			try {
				Hello hello = (Hello) factory.create(Hello.class, url);
				HelloVo vo = new HelloVo(params + i, params + i);
				HelloVo temp = hello.getHelloVo(vo);
				TestClient.successLong.incrementAndGet();
			} catch (Exception e) {
				TestClient.errorLong.incrementAndGet();
				System.out.println("TimeOut Size is:"+TestClient.errorLong.get()+"\tSuccess size is:"+TestClient.successLong.get());
				//e.printStackTrace();
			}
			//System.err.println(temp);
		}
	}

	
	public Long call() throws Exception {
		long startTime=System.currentTimeMillis();
		run();
		return System.currentTimeMillis()-startTime;
	}

}

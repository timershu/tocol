package tocol.rpc.test;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestServer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext-tocol-server.xml");
		context.start();
	}

}

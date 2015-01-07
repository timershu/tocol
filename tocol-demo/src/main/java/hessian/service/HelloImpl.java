package hessian.service;

public class HelloImpl implements Hello {

	
	public String test(String arg0) {
		System.out.println(arg0);
		return arg0 + "--Success";
	}

	
	public HelloVo getHelloVo(HelloVo hello) {
		// TODO Auto-generated method stub
		hello.setName(hello.getName() + "---Success");
		hello.setPassword(hello.getPassword() + "--Success");
		return hello;
	}

}

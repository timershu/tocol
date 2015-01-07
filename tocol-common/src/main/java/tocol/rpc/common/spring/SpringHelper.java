package tocol.rpc.common.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import tocol.rpc.common.BeanTocol;

public class SpringHelper implements ApplicationContextAware,BeanTocol {

	private ApplicationContext applicationContext;
	
	public Object getBean(String arg0){
		return applicationContext.getBean(arg0);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		this.applicationContext=arg0;
	}

}

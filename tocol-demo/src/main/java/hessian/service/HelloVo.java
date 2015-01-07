package hessian.service;

import java.io.Serializable;

public class HelloVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;

	@Override
	public String toString() {
		return "HelloVo [name=" + name + ", password=" + password + "]";
	}

	public HelloVo(String name, String password) {
		super();
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

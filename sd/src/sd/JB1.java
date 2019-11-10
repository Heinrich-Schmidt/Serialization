package sd;

import java.io.Serializable;


public class JB1 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8705777984960668181L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private String name;
	private short age;
	
	public JB1() {
	    this.name = "Ivan";
	    this.age = 21;
	}
	public JB1(String name, short age) {
	    this.name = name;
	    this.age = age;
	}
	 
	public String getName() {
	    return name;
	}
	 
	public void setName(String name) {
	    this.name = name;
	}
	    
	public short getAge() {
	    return age;
	}
	 
	public void setAge(short age) {
	    this.age = age;
	}


}
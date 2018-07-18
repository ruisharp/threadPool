package test.aop;

import test.Pojo;

public class AopTest {
	@Test
	public String test(Pojo pojo){
	try {
		System.out.println(pojo.getAsd());
		return pojo.getAsd();
		
	} catch (Exception e) {
		throw new RuntimeException();
	}
	  
		
	}
	
	public static void main(String[] args) {
		AopTest aopTest = new AopTest();
		Pojo pojo = new Pojo();
		pojo.setAsd("test");
		aopTest.test(pojo);
	}

}

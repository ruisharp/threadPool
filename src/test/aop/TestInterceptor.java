package test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import test.Pojo;

@Aspect
public class TestInterceptor {
	//@Around(value = "execution(*test.aop.*.*(..)) && @annotation(Test)") 
	//@Around("within(test.aop..*) && @annotation(Test)")
	 @Around("execution(* *.*(..)) && @annotation(test)")
	public Object doAroundMethod(ProceedingJoinPoint pjd,Test test) throws Throwable {
		Object[] o = pjd.getArgs();
        Pojo pojo = (Pojo) o[0];
        System.out.println(pojo.getAsd());
        Object result = pjd.proceed();
        if("test".equals(result)) {
        	System.out.println("123");
        }else {
        	System.out.println("456");
        }
		return null;
		
	}
}

package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolTest {
	
	/***
	 * corePoolSize �����߳�����ָ�������̳߳ش�С��������maximumPoolSizeֵʱ���̳߳��������corePoolSize ���̹߳������� 
maximumPoolSize ָ�����̳߳ص�����С���̳߳��������corePoolSize ���߳̿����У��� 
keepAliveTime ָ���ǿ����߳̽����ĳ�ʱʱ�䣨��һ���̲߳�����ʱ����keepAliveTime ��ʱ�佫ֹͣ���̣߳��� 
unit ��һ��ö�٣���ʾ keepAliveTime �ĵ�λ����NANOSECONDS, MICROSECONDS, MILLISECONDS, SECONDS, MINUTES, HOURS, DAYS��7����ѡֵ���� 
workQueue ��ʾ�������Ķ��У������Ҫ���̳߳�ִ�е��̶߳��У��� 
handler �ܾ����ԣ��������ʧ�ܺ���δ��������.

1���̳߳ظմ���ʱ������û��һ���̡߳������������Ϊ�����������ġ�������������������������̳߳�Ҳ��������ִ�����ǡ�
2�������� execute() �������һ������ʱ���̳߳ػ��������жϣ�
    a. ����������е��߳�����С�� corePoolSize����ô���ϴ����߳������������
    b. ����������е��߳��������ڻ���� corePoolSize����ô��������������С�
    c. �����ʱ��������ˣ������������е��߳�����С�� maximumPoolSize����ô����Ҫ�����߳������������
    d. ����������ˣ������������е��߳��������ڻ���� maximumPoolSize����ô�̳߳ػ��׳��쳣�����ߵ����ߡ��Ҳ����ٽ��������ˡ���
3����һ���߳��������ʱ������Ӷ�����ȡ��һ��������ִ�С�
4����һ���߳����¿���������һ����ʱ�䣨keepAliveTime��ʱ���̳߳ػ��жϣ������ǰ���е��߳������� corePoolSize����ô����߳̾ͱ�ͣ���������̳߳ص�����������ɺ������ջ������� corePoolSize �Ĵ�С��
       �������˵�����������ȼ��������һ������ִ�С�������д�СΪ 4��corePoolSizeΪ2��maximumPoolSizeΪ6����ô������15������ʱ��ִ�е�˳����������������ִ������ 1��2��Ȼ������3~6��������С���ʱ��������ˣ�����7��8��9��10 �ᱻ����ִ�У������� 11~15 ����׳��쳣������˳���ǣ�1��2��7��8��9��10��3��4��5��6����Ȼ������������ָ����С��ArrayBlockingQueue<Runnable>��˵�������LinkedBlockingQueue<Runnable>����Ϊ�ö����޴�С���ƣ����Բ������������⡣
	 * 
	 */
	
	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(2000, 4000, 200, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
		CountDownLatch threadSignal = new CountDownLatch(5);
		for(int j = 0 ; j <2001 ; j++) {
			List<String> strings = new ArrayList<String>();
			int i = 1;
			while(true) {
				strings.add("test:"+j+":"+i);
				i++;
				if(i >= 4) {
					break;
				}
				
			}
			
			MyTest myTest = new MyTest(strings , threadSignal , executor);
			executor.execute(myTest);

			
		}
		executor.shutdown();
	}
	
	
	   static class MyTest extends Thread {
		  	private CountDownLatch threadSignal;
	    	
	    	private List<String> list;
	    	
	    	ThreadPoolExecutor executor;
	    	
	    	
	        public MyTest(List<String> list , CountDownLatch threadSignal , ThreadPoolExecutor executor) {
	        	this.list = list;
	        	this.threadSignal = threadSignal;
	        	this.executor = executor;
	        }

	        @Override
	        public void run() {
	        	for(String s : list) {
	        		System.out.println(s);
	        	}
	        	System.out.println("����һ��");
	        	System.out.println("�̳߳����߳���Ŀ��"+executor.getPoolSize()+"�������еȴ�ִ�е�������Ŀ��"+ executor.getQueue().size()+"����ִ������������Ŀ��"+executor.getCompletedTaskCount());
	        	threadSignal.countDown();
	       	       	try {
				threadSignal.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 
	        }
	        
			public List<String> getList() {
				return list;
			}

			public void setList(List<String> list) {
				this.list = list;
			}

			public CountDownLatch getThreadSignal() {
				return threadSignal;
			}

			public void setThreadSignal(CountDownLatch threadSignal) {
				this.threadSignal = threadSignal;
			}
			
			
	        
	    }
	

}

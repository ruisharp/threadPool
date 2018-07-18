package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
	
	private static List<String> strings = new ArrayList<String>();
	private static int poolSize = 5;
    public static void main(String[] args) throws InterruptedException {
    	int i = 1;
		while(true) {
			strings.add("test:"+i);
			i++;
			if(i >= 20) {
				break;
			}
			
		}
			int size  = strings.size();
			int limit = size/poolSize;
			CountDownLatch threadSignal = null;
			ExecutorService executor = null;
			if(0 == limit) {
				executor = Executors.newFixedThreadPool(1);
				threadSignal = new CountDownLatch(1);
				MyTest myTest = new MyTest(strings, null);
				executor.execute(myTest);
			}else {
				threadSignal = new CountDownLatch(poolSize);
				executor = Executors.newFixedThreadPool(poolSize);
				for(int j = 0; j < poolSize; j++) {
					MyTest myTest;
					if(j+1>limit) {
						myTest = new MyTest(strings.subList(j* limit, size),threadSignal);
						//threadSignal.await();
					}else {
						myTest = new MyTest(strings.subList(j*limit, (j+1)*limit), threadSignal);
						//threadSignal.await();
					}
					executor.execute(myTest);
				}
			}
			if (null != threadSignal && null != executor) {
				try {
					threadSignal.await();
				} catch (Exception e) {
					System.out.println("线程池管理出现异常！");
				}
				if (threadSignal.getCount() == 0) {
					executor.shutdown();
				}
			}

		System.out.println("全部搞完了");
	}
    
   static class MyTest extends Thread {
    	
    	private List<String> list;
    	
    	private CountDownLatch threadSignal;
    	
        public MyTest(List<String> list,CountDownLatch threadSignal) {
        	this.list = list;
        	this.threadSignal = threadSignal;
        }

        @Override
        public void run() {
        	for(String s : list) {
        		System.out.println(s);
        	}
        	System.out.println("搞完一组");
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

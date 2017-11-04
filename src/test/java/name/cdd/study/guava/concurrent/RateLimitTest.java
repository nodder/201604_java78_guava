package name.cdd.study.guava.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

import junit.framework.TestCase;

//RateLimiter������JDK���ź���Semphore�����������ƶ���Դ�������ʵ��߳���  
public class RateLimitTest extends TestCase
{
    @Override
    protected void tearDown() throws Exception
    {
        Thread.sleep(6000);
    }
    
    public void testRateLimit()
    {
        ListeningExecutorService executorService = MoreExecutors  
                        .listeningDecorator(Executors.newCachedThreadPool());  
        
        RateLimiter limit = RateLimiter.create(1);//ÿ�벢���߳������ƣ���ʵ�������Ͽ����о�������ֵ���Ƹߡ�
        
        for(int i = 0; i < 10; i++)
        {
            limit.acquire();
            executorService.submit(new Task(i));  
        }
    }
    
    private class Task implements Callable<Integer>
    {
        private int count;

        public Task(int count)
        {
            this.count = count;
        }
        
        @Override
        public Integer call() throws InterruptedException
        {
            int result = count + 100;
            System.out.println("in call, result == " + result);
            waitAWhile();
           
            return result;
        }

        private void waitAWhile()
        {
            try
            {
                Thread.sleep(2000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}

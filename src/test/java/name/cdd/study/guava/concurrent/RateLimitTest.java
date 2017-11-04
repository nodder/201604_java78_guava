package name.cdd.study.guava.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;

import junit.framework.TestCase;

//RateLimiter类似于JDK的信号量Semphore，他用来限制对资源并发访问的线程数  
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
        
        RateLimiter limit = RateLimiter.create(1);//每秒并发线程数限制，从实际运行上看，感觉比配置值限制高。
        
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

package name.cdd.study.guava.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;

import junit.framework.TestCase;

//注：书中的Futures.withFallback和Futures.transform方法，均已经过时，并在guava20.0移除。不在此练习。

public class AsyncFunctionTest extends TestCase
{
    private ExecutorService executorService;
    private ListeningExecutorService listeningExecutorService;
    
    protected void setUp()
    {
        executorService = Executors.newCachedThreadPool();
        listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
    };
    
    public void testAsyncFunction() throws Exception
    {
        AsyncFuntionSample asyncFunc = new AsyncFuntionSample();
        
        ListenableFuture<String> future = asyncFunc.apply(5L);
        assertEquals("5900", future.get());
        assertEquals(1, asyncFunc.map.size());
        
        future = asyncFunc.apply(5L);
        assertEquals("5900", future.get());
        assertEquals(1, asyncFunc.map.size());
        
        future = asyncFunc.apply(6L);
        assertEquals("6901", future.get());
        assertEquals(2, asyncFunc.map.size());
    }
    
    private class AsyncFuntionSample implements AsyncFunction<Long, String>
    {
        private ConcurrentMap<Long, String> map = Maps.newConcurrentMap();
        private int prefix = 900;
        
        @Override
        public ListenableFuture<String> apply(final Long input) throws Exception
        {
            if(map.containsKey(input))
            {
                SettableFuture<String> listenableFuture = SettableFuture.create();
                listenableFuture.set(map.get(input));
                return listenableFuture;
            }
            else
            {
                return listeningExecutorService.submit(new Callable<String>()
                {
                    @Override
                    public String call() throws Exception
                    {
                        String value = getValueFromService(input);
                        map.putIfAbsent(input, value);
                        return value;
                    }
                });
                
            }
        }

        private String getValueFromService(Long key) throws InterruptedException
        {
            Thread.sleep(1);
            return String.valueOf(key) + String.valueOf(prefix++);
        }
        
    }
}

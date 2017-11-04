package name.cdd.study.guava.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import junit.framework.TestCase;

public class ListenableFutureTest extends TestCase
{
    private int count;
    private ExecutorService executorService;
    
    @Override
    protected void setUp() throws Exception
    {
        count = 0;
        executorService = Executors.newCachedThreadPool();
    }
    
    @Override
    protected void tearDown() throws Exception
    {
        Thread.sleep(100);//�첽��������Ҫ�ȴ�
        executorService.shutdown();
    }
    
    //JDK���õ�����
    public void testFeature() throws InterruptedException, ExecutionException
    {
        Future<Integer> future = executorService.submit(new Task(count));
        assertEquals(100, (int)future.get());
    }
    
    //Future�ص��ĵ�һ�ַ���
    public void testListenableFuture() throws InterruptedException, ExecutionException
    {
        ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(executorService);
        final ListenableFuture<Integer> future = listeningExecutor.submit(new Task(count++));
        
        future.addListener(new ResultRunnable(100, future), executorService); 
        
//        future.addListener(new Runnable()  //����������д��
//        {
//            public void run()
//            {
//                try
//                {
//                    int actual = (int)future.get();
//                    System.out.println("in runnable2, actual==" + actual);
//                    assertEquals(100, actual);
//                }
//                catch(InterruptedException | ExecutionException e)
//                {
//                    fail();
//                }
//            }
//        }, executorService);//��һ������Runnable���ڵڶ�������executorService��ִ�С�
    }
    
    //Feature�ص��ĵڶ��ַ��������˸�ϲ�����֡�
    public void testFutureCallback()
    {
        ListeningExecutorService listeningExecutor = MoreExecutors.listeningDecorator(executorService);
        final ListenableFuture<Integer> future = listeningExecutor.submit(new Task(count++));
        
        Futures.addCallback(future, new MyFutuaCallback(100));
    }
    
    
    private class ResultRunnable implements Runnable
    {
        private int expected;
        private ListenableFuture<Integer> future;
        
        public ResultRunnable(int expected, ListenableFuture<Integer> future)
        {
            this.expected = expected;
            this.future = future;
        }
        
        @Override
        public void run()
        {
            try
            {
                assertEquals(expected, (int)future.get());
                System.out.println("in runnable, actual==" + expected);
            }
            catch(InterruptedException | ExecutionException e)
            {
                fail();
            }
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
        public Integer call() throws Exception
        {
            Thread.sleep(1);
            return count + 100;
        }
    }
    
    private class MyFutuaCallback implements FutureCallback<Integer>{

        private int expected;

        public MyFutuaCallback(int expected)
        {
            this.expected = expected;
        }

        @Override
        public void onSuccess(Integer result)
        {
            System.out.println("in MyFeatureCallback result==" + result);
            assertEquals(expected, (int)result);
        }

        @Override
        public void onFailure(Throwable t)
        {
            fail();
        }
        
    }
}

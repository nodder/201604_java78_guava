package name.cdd.study.java8;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import junit.framework.TestCase;

public class Chapter3 extends TestCase
{
    private static Logger logger = Logger.getLogger(Chapter3.class.getName());
    
    static
    {
        logger.setLevel(Level.ALL);
    }
    
    public void test1()
    {
        String[] a = Stream.generate(() -> "abc").limit(20).toArray(String[]::new);
        Integer index = 10;
        
        logIf(Level.FINEST, () -> index == 10, () -> "a[10] = " + a[10]);
    }
    
    /** 注意：第三个参数必须是Supplier，才可以延时加载 */
    /** 这个例子挺实用  */
    private void logIf(Level level, MyPredicate pre, Supplier<String> message)
    {
        if(logger.isLoggable(level))
        {
            if(pre.test())
            {
                logger.log(level, message.get());
                System.out.println(message.get());
            }
        }
    }
    
    interface MyPredicate
    {
        public boolean test();
    }

    /** TODO 重读ReentrantLock相关 */
    public void test2()
    {
        ReentrantLock myLock = new ReentrantLock();
        
        String message = "in lock";
        withLock(myLock, () -> System.out.println(message));
    }
    
    public <T> void withLock(ReentrantLock lock, MyConsumer c)
    {
        lock.lock();
        
        try
        {
            c.accept();
        }
        finally
        {
            lock.unlock();
        }
    }
    
    interface MyConsumer
    {
        void accept();
    }
    
    public void test16()
    {
        doInOrderAsync(() -> 100, (t, th) -> {
            assertNotNull(t);
            assertNull(th);
            assertEquals("100", t.toString());
            });
        
        doInOrderAsync(() -> 10/0, (t, th) -> {
            assertNull(t);
            assertNotNull(th);
            assertEquals(ArithmeticException.class, th.getClass());
            });
    }
    
    private <T> void doInOrderAsync(Supplier<T> first, BiConsumer<T, Throwable> second)
    {
        new Thread
        (
            () -> {
                    T result = null;
                    Throwable throwable = null;
                    
                    try
                    {
                        result = first.get();
                    }
                    catch(Throwable th)
                    {
                        throwable = th;  
                    }
                    
                    second.accept(result, throwable);
                  }
        ).start();
    }
    
    //test 20
    public static <T, U> List<U> map(List<T> list, Function<T, U> func)
    {
        return list.stream().map(func).collect(toList());
    }
}

package name.cdd.study.guava.concurrent;

import java.util.ArrayList;
import java.util.List;

import com.google.common.util.concurrent.Monitor;

import junit.framework.TestCase;

/**
 * enter()：进入到当前Monitor，无限期阻塞，等待锁。
 * enter(long time, TimeUnit unit)：进入到当前Monitor，最多阻塞给定的时间，返回是否进入Monitor。
 * tryEnter()：如果可以的话立即进入Monitor，不阻塞，返回是否进入Monitor。
 * enterWhen(Guard guard)：进入当前Monitor，等待Guard的isSatisfied()为true后，继续往下执行 ，但可能会被打断。
 * enterIf(Guard guard)：如果Guard的isSatisfied()为true，进入当前Monitor。等待获得锁，不需要等待Guard satisfied。
 * tryEnterIf(Guard guard)：如果Guard的isSatisfied()为true并且可以的话立即进入Monitor，不等待获取锁，也不等待Guard satisfied。
 * 
 * 代替java中的wait、notify，以及java5之后的ReentrantLock，不需要while循环就可以实现多线程的访问限定。且可读性更强。
 * 参考网页：http://www.linuxidc.com/Linux/2015-03/115526.htm
 * 
 * @author 10087118
 *
 */
public class MonitorTest extends TestCase
{
    private final int MAX_SIZE = 2;
    
    private final List<String> list = new ArrayList<String>();
    private Monitor monitor = new Monitor();
    
    private Monitor.Guard listBelowCapacity = new Monitor.Guard(monitor)
    {
        @Override
        public boolean isSatisfied()
        {
            return list.size() < MAX_SIZE;
        }
    };
    
    public void testEnterWhen() throws InterruptedException
    {
        addToList_enterWhen("a");
        assertEquals(1, list.size());
        
        addToList_enterWhen("b");
        assertEquals(2, list.size());
        
//        addToList_enterWhen("c"); //cannot add, waiting forever
//        assertEquals(3, list.size());
    }
    
    public void testEnterIf()
    {
        addToList_enterIf("a");
        assertEquals(1, list.size());
        
        addToList_enterIf("b");
        assertEquals(2, list.size());
        
        addToList_enterIf("c");
        assertEquals(2, list.size()); //actually not added
    }

    private void addToList_enterIf(String ele)
    {
        if(monitor.enterIf(listBelowCapacity))
        {
            try
            {
                list.add(ele);
            }
            finally
            {
                monitor.leave();
            }
        }
    }

    private void addToList_enterWhen(String ele) throws InterruptedException
    {
        monitor.enterWhen(listBelowCapacity);
        try
        {
            list.add(ele);
        }
        finally
        {
            monitor.leave();
        }
    }
}

package name.cdd.study.guava.concurrent;

import java.util.ArrayList;
import java.util.List;

import com.google.common.util.concurrent.Monitor;

import junit.framework.TestCase;

/**
 * enter()�����뵽��ǰMonitor���������������ȴ�����
 * enter(long time, TimeUnit unit)�����뵽��ǰMonitor���������������ʱ�䣬�����Ƿ����Monitor��
 * tryEnter()��������ԵĻ���������Monitor���������������Ƿ����Monitor��
 * enterWhen(Guard guard)�����뵱ǰMonitor���ȴ�Guard��isSatisfied()Ϊtrue�󣬼�������ִ�� �������ܻᱻ��ϡ�
 * enterIf(Guard guard)�����Guard��isSatisfied()Ϊtrue�����뵱ǰMonitor���ȴ������������Ҫ�ȴ�Guard satisfied��
 * tryEnterIf(Guard guard)�����Guard��isSatisfied()Ϊtrue���ҿ��ԵĻ���������Monitor�����ȴ���ȡ����Ҳ���ȴ�Guard satisfied��
 * 
 * ����java�е�wait��notify���Լ�java5֮���ReentrantLock������Ҫwhileѭ���Ϳ���ʵ�ֶ��̵߳ķ����޶����ҿɶ��Ը�ǿ��
 * �ο���ҳ��http://www.linuxidc.com/Linux/2015-03/115526.htm
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

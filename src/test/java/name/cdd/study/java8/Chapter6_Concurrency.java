package name.cdd.study.java8;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

import com.google.common.util.concurrent.AtomicLongMap;

import junit.framework.TestCase;

public class Chapter6_Concurrency extends TestCase
{
    /**AtomicLong的实现方式是内部有个value 变量，当多线程并发自增，自减时，均通
                 过CAS 指令从机器指令级别操作保证并发的原子性
       CAS(Compare and Set)的语义是“我认为V的值应该为A，如果 是，那么将V的值更新为B，否则不修改并
                告诉V的值实际为多少”，CAS是项乐观锁技术，当多个线程尝试使用CAS同时更新同
                一个变量时，只有其中一个线 程能更新变量的值，而其它线程都失败，失败的线程
                并不会被挂起，而是被告知这次竞争中失败，并可以再次尝试。 */
    public void testAtomicLong()
    {
        AtomicLong largest = new AtomicLong(-1);
        largest.updateAndGet(current -> Math.max(current, 2));//内部实现是while循环
        assertEquals(2, largest.get());
        
//        largest.accumulateAndGet(5, (current, newOne) -> Math.max(current, newOne));
        largest.accumulateAndGet(5, Math::max);
        assertEquals(5, largest.get());
    }
    
    //LongAdder的效率更高，参考如下。建议永远使用LongerAdder（开始于java8）
    //http://coolshell.cn/articles/11454.html
    public void testLongAdder()
    {
        LongAdder adder = new LongAdder();
        adder.increment();
        adder.increment();
        adder.add(2);
        assertEquals(2, adder.intValue());
        assertEquals(2, adder.sum());
    }
    
    public void testLongAccumulator()
    {
        LongAccumulator acc = new LongAccumulator((x, y) -> x + y, 0);
        acc.accumulate(1);
        acc.accumulate(2);
        acc.accumulate(3);
        assertEquals(6, acc.longValue());
    }
    
    //乐观锁 悲观锁的概念？
    
    //不能完全确定下面写法的正确性。需要写多线程的用例验证。
    public void testConcurrentMap()
    {
        ConcurrentMap<String, Long> map = new ConcurrentHashMap<String, Long>();
        map.put("key", 1L);
        
        updateMethod1(map);
        updateMethod2();
        updateMethod3(map);
        updateMethod4(map);
        updateMethod5();//guava 
    }

    private void updateMethod5()
    {
        AtomicLongMap<String> map = AtomicLongMap.create();
        map.incrementAndGet("key");
    }

    private void updateMethod4(ConcurrentMap<String, Long> map)
    {
//        map.merge("key", 1L, (existV, newV) -> existV + newV);
        map.merge("key", 1L, Long::sum);
    }

    private void updateMethod3(ConcurrentMap<String, Long> map)
    {
        map.compute("key", (key, oldValue) -> oldValue == null ? 1L : oldValue + 1);
    }

    //方法2：将value做成线程安全的。
    private void updateMethod2()
    {
        ConcurrentMap<String, LongAdder> map = new ConcurrentHashMap<String, LongAdder>();
        map.putIfAbsent("key", new LongAdder()).increment();
    }

    //方法1：利用了支持CAS语义的replace方法
    private void updateMethod1(ConcurrentMap<String, Long> map)
    {
        Long oldValue;
        long newValue;
        do
        {
            oldValue = map.get("key");
            newValue = oldValue == null ? 1L : oldValue + 1;
        }
        while(!map.replace("key", oldValue, newValue));
    }
    
}

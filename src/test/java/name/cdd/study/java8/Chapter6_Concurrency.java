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
    /**AtomicLong��ʵ�ַ�ʽ���ڲ��и�value �����������̲߳����������Լ�ʱ����ͨ
                 ��CAS ָ��ӻ���ָ��������֤������ԭ����
       CAS(Compare and Set)�������ǡ�����ΪV��ֵӦ��ΪA����� �ǣ���ô��V��ֵ����ΪB�������޸Ĳ�
                ����V��ֵʵ��Ϊ���١���CAS�����ֹ���������������̳߳���ʹ��CASͬʱ����ͬ
                һ������ʱ��ֻ������һ���� ���ܸ��±�����ֵ���������̶߳�ʧ�ܣ�ʧ�ܵ��߳�
                �����ᱻ���𣬶��Ǳ���֪��ξ�����ʧ�ܣ��������ٴγ��ԡ� */
    public void testAtomicLong()
    {
        AtomicLong largest = new AtomicLong(-1);
        largest.updateAndGet(current -> Math.max(current, 2));//�ڲ�ʵ����whileѭ��
        assertEquals(2, largest.get());
        
//        largest.accumulateAndGet(5, (current, newOne) -> Math.max(current, newOne));
        largest.accumulateAndGet(5, Math::max);
        assertEquals(5, largest.get());
    }
    
    //LongAdder��Ч�ʸ��ߣ��ο����¡�������Զʹ��LongerAdder����ʼ��java8��
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
    
    //�ֹ��� �������ĸ��
    
    //������ȫȷ������д������ȷ�ԡ���Ҫд���̵߳�������֤��
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

    //����2����value�����̰߳�ȫ�ġ�
    private void updateMethod2()
    {
        ConcurrentMap<String, LongAdder> map = new ConcurrentHashMap<String, LongAdder>();
        map.putIfAbsent("key", new LongAdder()).increment();
    }

    //����1��������֧��CAS�����replace����
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

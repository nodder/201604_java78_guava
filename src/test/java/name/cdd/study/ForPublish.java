package name.cdd.study;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import junit.framework.TestCase;

public class ForPublish extends TestCase
{
    /** 简化代码 */
    public void test1()
    {
        String[] strings = {"aaa", "b", "cc"};
        
       
        
        Arrays.sort(strings, (String s1, String s2) -> Integer.compare(s1.length(), s2.length()));
        //类型推断
        Arrays.sort(strings, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        Arrays.sort(strings, Comparator.comparing(String::length));
        
        
        Arrays.sort(strings, new LengthComparator());
    }
    
    class LengthComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            return Integer.compare(s1.length(), s2.length());
        }
    }
    
    public void test2()
    {
        Integer[] seqMeter = new Integer[10];
        for(int i = 0; i < 10; i++)
        {
            seqMeter[i] = i + 1;
        }
        
        Double[] seqInch = new Double[seqMeter.length];
        for(int i = 0; i < seqInch.length; i++)
        {
            seqInch[i] = seqMeter[i] * 39.37;
        }
        
        double sum = 0;
        for(int i = 0; i < seqInch.length; i++)
        {
            sum += seqInch[i];
        }
        
        System.out.println(sum);
        
        /////////////////////////////////////////////
        double sum2 = Stream.iterate(1, i -> i + 1).limit(10).mapToDouble(i -> i * 39.37).sum();
        System.out.println(sum2);
        
    }
    
    public void test3()
    {
        List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        
//        int sum = nums.stream().filter(Objects::nonNull).distinct()
//                               .mapToInt(num -> num * 2).skip(2).limit(4)
//                               .peek(System.out::println).sum();
//        
        int sum = nums.stream().parallel().filter(Objects::nonNull).distinct()
        .mapToInt(num -> num * 2).skip(2).limit(4)
        .peek(System.out::println).sum();
        
        System.out.println("sum is:" + sum);
    }
    
    @SuppressWarnings ("null")
    public void test4()
    {
        Logger logger = null;
        int x = 0, y = 0;
        logger.info("x: " + x + ", y: " + y);

    }
    
    public void test5() throws IOException
    {
        try (Stream<String> lines = Files.lines(Paths.get("c:/test.txt"))) {
            Optional<String> passwordEntry
            = lines.filter(s -> s.contains("password")).findFirst();
            
            System.out.println(passwordEntry.orElse("not found"));
            } // The stream, and hence the file, will be closed here
        
        
        try (Stream<String> lines = Files.lines(Paths.get("c:/test.txt"))) {
            Optional<String> passwordEntry
            = lines.filter(s -> s.contains("password")).onClose(() -> System.out.println("closing")).findFirst();
            
            System.out.println(passwordEntry.orElse("not found"));
            } // The stream, and hence the file, will be closed here
    }
    
    public void test6() throws IOException
    {
        assertEquals(1000000, 1_000_000);
        
        Files.walk(Paths.get("d:\\maven"), FileVisitOption.FOLLOW_LINKS);
        
        assertEquals("c:/ftpdir", String.join("/", "c:", "ftpdir"));
        assertEquals("c:/ftpdir", Joiner.on('/').join("c:", "ftpdir"));
    }
    
    public int readDuration(Properties props, String name)
    {
        String value = props.getProperty(name);
        if(value != null)
        {
            int i = Integer.parseInt(value);
            if(i > 0)
            {
                return i;
            }
        }
        
        return 0;
    }
}

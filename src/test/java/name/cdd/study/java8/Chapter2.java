package name.cdd.study.java8;

import static java.lang.System.out;
import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import junit.framework.TestCase;

@SuppressWarnings ("unused")
public class Chapter2 extends TestCase
{
    public void test1() 
    {
        long result = Stream.of("aa", "bc", "c").filter(w -> w.length() > 1).count();
        
        assertEquals(2, result);
    }
    
    public void test2()
    {
        String line = "Verify that asking for the first five long words does not call the filter method "
                        + "once the fifth long word has been found. Simply log each method call";
        
        String[] result = Stream.of(line.split("[\\P{L}]+")).sorted(Comparator.comparing(String::length).reversed()).limit(5).peek(out::println).toArray(String[]::new);
        
        assertArrayEquals(result, new String[]{"Verify", "asking", "filter", "method", "Simply"});
    }
    
    public void test3() throws IOException
    {
        System.out.println("===============333333333============");
        assertEquals(5, Files.lines(Paths.get("a.txt")).flatMap(l -> Stream.of(l.split("[\\P{L}]+"))).peek(out::println).count());
        
        System.out.println("===============4444444444============");
        Stream<String> s = Files.lines(Paths.get("a.txt")).flatMap(l -> Stream.of(l.split("[\\P{L}]+")));//默认的返回值是Stream<Object>需要手工改为Stream<String>
        
        assertEquals(5, Stream.of(new String(Files.readAllBytes(Paths.get("a.txt"))).split("[\\P{L}]+")).peek(out::println).count());
    }
    
    public void test4()
    {
        int[] values = {1, 4, 9, 16};
        Stream<int[]> s1 = Stream.of(values);
        Stream<int[]> s2 = Stream.of(new int[] {1, 4, 9, 16});
        Stream<Integer> s3 = Stream.of(1, 4, 9, 16);
        IntStream s4 = Arrays.stream(values);
        IntStream s5 = s2.flatMapToInt(x -> Arrays.stream(x));
        Stream<Integer> s6 = s4.boxed();
        
        Stream<int[]> s7 = Stream.of(new int[] {1, 4, 9, 16}, new int[] {25, 36});
        IntStream s8 = s7.flatMapToInt(x -> Arrays.stream(x));
        s8.forEach(out::println);
        
        String[] valueStrs = {"1", "4", "9", "16"};
        Stream<String> ss = Stream.of(valueStrs);
    }
    
    public void test5()
    {
        final long a = 25214903917L, c = 11, m = (long)Math.pow(2, 48);
        
        Stream<Long> s = Stream.iterate(1L, x -> (a * x + c) % m);
        s.limit(5).forEach(out::println);
    }
    
    public void test6()
    {
        final String input = "abcd";
        
        Stream<Character> chars = IntStream.range(0, input.length()).mapToObj(i -> input.charAt(i));
        chars.forEach(out::println);
        
        Stream.of(0, 1, 2, 3).map(i -> input.charAt(i)).forEach(out::println);
        
        Stream.iterate(0, x -> x + 1).limit(input.length()).map(i -> input.charAt(i)).forEach(out::println);
    }
    
    public void test9()
    {
        Stream<ArrayList<Integer>> listStream = Stream.of(Lists.newArrayList(1, 2), Lists.newArrayList(4, 5), Lists.newArrayList(8, 9));
        List<Integer> result = listStream.flatMap(List::stream).collect(Collectors.toList());
        assertEquals(Lists.newArrayList(1, 2, 4, 5, 8, 9), result);
        
        listStream = Stream.of(Lists.newArrayList(1, 2), Lists.newArrayList(4, 5), Lists.newArrayList(8, 9));
        List<Integer> result2 = new ArrayList<Integer>();
        listStream.forEach(list -> result2.addAll(list));
        assertEquals(Lists.newArrayList(1, 2, 4, 5, 8, 9), result2);
        
        
        listStream = Stream.of(Lists.newArrayList(1, 2), Lists.newArrayList(4, 5), Lists.newArrayList(8, 9));
        ArrayList<Integer> result3 = listStream.reduce(new ArrayList<Integer>(), (x, y) -> {x.addAll(y); return x;});
        assertEquals(Lists.newArrayList(1, 2, 4, 5, 8, 9), result3);
        
        listStream = Stream.of(Lists.newArrayList(1, 2), Lists.newArrayList(4, 5), Lists.newArrayList(8, 9));
//        List<Integer> result4 = listStream.collect(() -> new ArrayList<Integer>(), (List<Integer> r, List<Integer> list) -> r.addAll(list), (left, right) -> left.addAll(right));
        List<Integer> result4 = listStream.collect(ArrayList::new, List::addAll, List::addAll);//同上面注释掉的一行
        assertEquals(Lists.newArrayList(1, 2, 4, 5, 8, 9), result4);
    }
    
    //double的平均值，可以先转化为DoubleStream；也可以使用Collectors中的averagingDouble或者summarizingDouble。
    //但不能使用先sum再除以count的方式，因为sum之后流已经被关闭了。
    public void test10()
    {
        Stream<Double> doubles = Stream.of(1.0d, 2.0d, 3.0d);
        DoubleStream doubleStream = doubles.mapToDouble(d -> d);
        assertEquals(2.0d, doubleStream.average().getAsDouble());
        
        doubles = Stream.of(1.0d, 2.0d, 3.0d);
        Double result = doubles.collect(Collectors.averagingDouble(d -> d));
        assertEquals(2.0d, result);
        
        doubles = Stream.of(1.0d, 2.0d, 3.0d);
        DoubleSummaryStatistics result2 = doubles.collect(Collectors.summarizingDouble(d -> d));
        assertEquals(2.0d, result2.getAverage());
    }
    
    public void test11()
    {
        String line = "Verify that asking for the first five long words does not call the filter method "
                        + "once the fifth long word has been found. Simply log each method call";
        
        Stream<String> words = Stream.of(line.split("[\\P{L}]+"));
        AtomicInteger count = new AtomicInteger(0);
        
        words.parallel().filter(w -> w.length() <= 4).forEach(s -> count.getAndIncrement());
        assertEquals(18,  count.get());
    }
    
    public void test12()
    {
        String line = "Verify that asking for the first five long words does not call the filter method "
                        + "once the fifth long word has been found. Simply log each method call";
        
        Stream<String> words = Stream.of(line.split("[\\P{L}]+"));
        
        Map<Boolean, Long> result = words.collect(Collectors.groupingByConcurrent(w -> w.length() <= 4, Collectors.counting()));
//        Map<Boolean, Long> result = words.parallel().collect(Collectors.groupingBy(w -> w.length() <= 4, Collectors.counting()));//效果相同
        assertEquals(18, result.get(true).intValue());
    }
    

}
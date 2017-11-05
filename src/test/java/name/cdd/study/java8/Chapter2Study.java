package name.cdd.study.java8;

import static java.lang.System.out;
import static org.junit.Assert.assertArrayEquals;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import junit.framework.TestCase;

public class Chapter2Study extends TestCase
{
    private static final String content = "The Files class";
    
    private Path path;
    
    private String[] words;
    
    @Override
    protected void setUp() throws Exception
    {
        path = Paths.get("testData/chapter2/sample.txt");
        Files.createDirectories(path.getParent());
        
        Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        
        String readContent = new String(Files.readAllBytes(path));
        words = readContent.split("[\\P{L}]+");
    }
    
    @SuppressWarnings ("unused")
    public void testStream() throws IOException
    {
        assertEquals(5050, Stream.iterate(1, x -> x + 1).limit(100).parallel().collect(Collectors.summarizingInt(x -> x)).getSum());
        assertEquals(5050, IntStream.rangeClosed(1, 100).sum());
        assertEquals(5050, IntStream.rangeClosed(1, 100).sum());
        assertEquals(5050, IntStream.rangeClosed(1, 100).reduce(Integer::sum).getAsInt());
        
        Stream<String> s = Stream.generate(() -> "aaa");
        s = Stream.generate(() ->"aaa").limit(100);
        
        Stream<Integer> empty = Stream.empty();
        Stream<Integer> intStream = Stream.iterate(1, i -> i + 1);
        Stream.iterate(BigInteger.ZERO, b -> b.add(BigInteger.ONE));

        try(Stream<String> lines = Files.lines(path))
        {
        }
        String readContent = new String(Files.readAllBytes(path));
        String[] words = readContent.split("[\\P{L}]+");
        
        assertEquals(1, Stream.of(words).filter(w -> w.length() < 4).count());
        assertEquals(1, Stream.of(words).parallel().filter(w -> w.length() < 4).count());
        
        assertArrayEquals(new String[] {"the", "files", "class"}, Stream.of(words).map(w -> w.toLowerCase()).toArray(String[]::new));
        
        Stream.of(words).skip(1);//略过第一个元素
        
        //注意：如果没有toArray方法，则不会打印。
        Stream.iterate(1, i -> i + 10).peek(System.out::println).limit(5).toArray();
        
        //peek放在后面也可以。
        Stream.iterate(1000, i -> i + 11).limit(5).peek(System.out::println).toArray();
        
        //有状态的转换——去重复。toArray方法只是为了打印，与前几个例子目的一样。
        Stream.of("11", "44", "44", "22").distinct().peek(System.out::println).toArray(String[]::new);
        
        System.out.println("===================1111111===========");
        //排序。count方法只是为了打印，与前几个例子目的一样。
        Stream.of("11", "44", "44", "22").sorted().peek(System.out::println).count();
        
        System.out.println("===================22222===========");
        Stream.of("55555","22","333").sorted(Comparator.comparing(String::length)).peek(System.out::println).count();
        
        System.out.println("===================33333===========");
        Stream.of("55555","22","333").sorted(Comparator.comparing(String::length).reversed()).peek(out::println).count();
        
        System.out.println("===================55555===========");
        List<Integer> nums = Lists.newArrayList(1,1,null,2,3,4,null,5,6,7,8,9,10);
        System.out.println("sum is:"+nums.stream().filter(num -> num != null).distinct().mapToInt(num -> num * 2).peek(System.out::println).skip(2).limit(4).sum());

        System.out.println("===================6666===========");
        Arrays.stream(new int[] {1, 2, 3}, 0, 2).peek(out::println).count();
        System.out.println("===================777===========");
        IntStream.range(10, 15).peek(out::println).count();//rangeClosed
        
        IntStream intStream1 = IntStream.range(0, 3);
        Stream<Integer> integerStream = IntStream.range(0, 3).boxed();
    }
    
    public void testOptional()
    {
        Optional<String> max = Stream.of("cc", "bb", "aa", "zz").max(String::compareToIgnoreCase);
        System.out.println(max.isPresent() ? max.get() : "null");
        System.out.println(max.orElse("null"));//orElseThrow
        
        Integer integer = 1;
        Optional<Object> result = Optional.ofNullable(integer).map(i -> i + 1).map(i -> i * 10);
        System.out.println("===================4444===========");
        System.out.println(result.orElse("NA"));
    }
    
    public void testReduce()
    {
        Stream<Integer> stream = Stream.iterate(1, i -> i+1).limit(100);
        assertEquals(5050, stream.reduce((x, y) -> x + y).get().intValue());
        
        stream = Stream.iterate(1, i -> i+1).limit(100);
        assertEquals(5050, stream.reduce(Integer::sum).get().intValue());
        
        stream = Stream.iterate(1, i -> i+1).limit(100);
        assertEquals(5100, stream.reduce(50, Integer::sum).intValue());
    }
    
    @SuppressWarnings ("unused")
    public void testCollect() throws IOException
    {
        List<String> list = Files.lines(path).collect(Collectors.toList());
        List<String> list2 = Files.lines(path).collect(ArrayList::new, List::add, List::addAll);
        List<String> list3 = Files.lines(path).collect(() -> new ArrayList<String>(), (tmpList, e) -> tmpList.add(e), (left, right) -> left.addAll(right));
        
        
        Set<String> set = Files.lines(path).collect(Collectors.toSet());
        TreeSet<String> set2 = Files.lines(path).collect(Collectors.toCollection(TreeSet::new));
        
        //Collectors.toMap 和 Collectors.groupingBy
        
        assertEquals("The, Files, class", Stream.of(words).collect(Collectors.joining(", ")));
        assertEquals("1, 2, 3", Stream.of(1, 2, 3).map(Object::toString).collect(Collectors.joining(", ")));
        
        IntSummaryStatistics summary = Stream.of(words).collect(Collectors.summarizingInt(String::length));
        assertEquals("The".length(), summary.getMin());
        
        //forEach是终止方法，而peek不是。
        Stream.of(words).forEach(out::println);
        
        //放弃有序获取速度
        Stream.of(words).parallel().unordered().limit(10);
    }
    
    public void testCollect_groupingBy()
    {
//        Stream<Locale> locales = Stream.empty();
//        
//        Map<String, List<Locale>> countryToLocales = locales.collect(
//            Collectors.groupingBy(Locale::getCountry));
//        
//        Map<String, Set<Locale>> countryToLocaleSet = locales.collect(
//            groupingBy(Locale::getCountry, toSet()));
//        
//        Map<String, Long> countryToLocaleCounts = locales.collect(
//            groupingBy(Locale::getCountry, counting()));
//        
//        Map<String, Integer> stateToCityPopulation = cities.collect(
//            groupingBy(City::getState, summingInt(City::getPopulation)));
//        
//        Map<String, City> stateToLargestCity = cities.collect(
//            groupingBy(City::getState,
//            maxBy(Comparator.comparing(City::getPopulation))));
//        
//        Map<String, Set<String>> countryToLanguages = locales.collect(
//            groupingBy(l -> l.getDisplayCountry(),
//            mapping(l -> l.getDisplayLanguage(),
//            toSet())));
//        
//        Map<String, IntSummaryStatistics> stateToCityPopulationSummary = cities.collect(
//            groupingBy(City::getState,
//            summarizingInt(City::getPopulation)));
//        
//        Map<String, String> stateToCityNames = cities.collect(
//            groupingBy(City::getState,
//            reducing("", City::getName,
//            (s, t) -> s.length() == 0 ? t : s + ", " + t)));
//        
//        Map<String, String> stateToCityNames = cities.collect(
//            groupingBy(City::getState,
//            mapping(City::getName,
//            joining(", "))));
    }
    
    public void testStream_groupingby()
    {
        //见StreamExercise_ori类
    }
    
    
}

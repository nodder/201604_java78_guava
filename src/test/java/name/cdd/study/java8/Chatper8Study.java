package name.cdd.study.java8;

import static java.lang.System.out;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

import com.google.common.base.Joiner;

import junit.framework.TestCase;
import name.cdd.study.guava.Person;

public class Chatper8Study extends TestCase
{
    public void testString()
    {
        assertEquals("c:/ftpdir", String.join("/", "c:", "ftpdir"));
        assertEquals("c:/ftpdir", Joiner.on('/').join("c:", "ftpdir"));
    }
    
    public void testNumber()
    {
        assertEquals(255, Byte.toUnsignedInt(new Byte("-1")));
        
        assertTrue(Integer.compareUnsigned(1, 2) < 0);
        assertTrue(Integer.compareUnsigned(-1, Integer.MAX_VALUE) > 0);
        assertTrue(Integer.compare(-1, Integer.MAX_VALUE) < 0);
    }
    
    //初学时，可多在Comparator类中寻找
    public void testComparator()
    {
        Person[] people = new Person[0];
        
        Arrays.sort(people, Comparator.comparing(Person::getLastName).thenComparing(Person::getAge));
        
        Arrays.sort(people, Comparator.comparing(Person::getLastName, (s, t) -> Integer.compare(s.length(), t.length())));
        Arrays.sort(people, Comparator.comparingInt(p -> p.getLastName().length()));//和上面一句作用相同
        
        Arrays.sort(people, Comparator.comparing(Person::getLastName, Comparator.nullsFirst(Comparator.naturalOrder())));
        
//        Collections.sort();list排序
        
//        ComparisonChain.start().compare().resultguava的用法，但好像只能用在Person类中，不能用在Arrays.sort方法中
    }
    
    public void testFile() throws IOException
    {
        @SuppressWarnings ("resource")
        Stream<Path> result = Files.walk(Paths.get("d:\\maven"), FileVisitOption.FOLLOW_LINKS);
        result.forEach(out::println);
    }
    
    public void tetNull()
    {
        Stream<String> stream1 = Stream.generate(() -> "aa").limit(19);
        Stream<String> stream2 = Stream.generate(() -> (String)null).limit(19);
        
        assertEquals(false, stream1.anyMatch(Objects::isNull));
        assertEquals(true, stream2.anyMatch(Objects::isNull));
        
        stream1.filter(Objects::nonNull);
    }
}

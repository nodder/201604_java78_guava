package name.cdd.study.guava;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

import junit.framework.TestCase;

public class CollectionsTest extends TestCase
{
    private Person person1;
    private Person person2;
    private Person person3;
    private Person person4;
    private List<Person> personList;

    @Override
    protected void setUp() throws Exception
    {
        this.person1 = new Person("Wilma", "Flintstone", 30, "F");
        this.person2 = new Person("Fred", "Flintstone", 32, "M");
        this.person3 = new Person("Betty", "Rubble", 31, "F");
        this.person4 = new Person("Barney", "Rubble", 33, "M");
        this.personList = Lists.newArrayList(person1, person2, person3, person4);
    }
    
    //这个例子可以对比ListOper.filter
    public void testFluetIterableFilter()
    {
        Iterable<Person> filtered = FluentIterable.from(personList).filter(new Predicate<Person>()
        {
            @Override
            public boolean apply(Person input)
            {
                return input.getAge() > 31;
            }
        });
        
        assertFalse(Iterables.contains(filtered, person1));
        assertTrue(Iterables.contains(filtered, person2));
        assertFalse(Iterables.contains(filtered, person3));
        assertTrue(Iterables.contains(filtered, person4));
    }
    
    //对比ListOper.map
    public void testFluetIterableTransform()
    {
        List<String> trans = FluentIterable.from(personList).transform(new Function<Person, String>()
        {
            @Override
            public String apply(Person input)
            {
                return Joiner.on("#").join(input.getLastName(), input.getFirstName(), input.getAge());
            }
        }).toList();
        
        assertEquals("Flintstone#Fred#32", trans.get(1));
    }
    
    public void testSet()
    {
        Set<String> s1 = Sets.newHashSet("1","2","3");
        Set<String> s2 = Sets.newHashSet("2","3","4");
        
        SetView<String> result = Sets.difference(s1,s2);
        assertEquals("[1]", result.toString());
        
        //elements that are contained in one set or the other set, but not contained in both. 
        result = Sets.symmetricDifference(s1, s2);
        assertEquals("[1, 4]", result.toString());
        
        //elements that are found in two Set instances
        result = Sets.intersection(s1, s2);
        assertEquals("[2, 3]", result.toString());
        
        result = Sets.union(s1, s2);
        assertEquals("[1, 2, 3, 4]", result.toString());
    }
    
    //找了很久的数据结构，两个key，一个value
    public void testHashBasedTable()
    {
        HashBasedTable<Integer, Integer, String> table = HashBasedTable.create();

        table.put(1, 1, "Rook");
        table.put(1, 2, "Knight");
        table.put(1, 3, "Bishop");
        table.put(2, 2, "Doctor");
        
        assertEquals(true, table.contains(1, 1));
        assertEquals(true, table.containsColumn(2));
        assertEquals(true, table.containsRow(1));
        assertEquals(true, table.containsValue("Rook"));
        
        assertEquals(null, table.get(3, 4));
        
        Map<Integer, String> columnMap = table.column(2);
        assertEquals(2, columnMap.size());
        assertEquals("Knight", columnMap.get(1));
        assertEquals("Doctor", columnMap.get(2));
        
        Map<Integer, String> rowMap = table.row(2);
        assertEquals(1, rowMap.size());
        assertEquals("Doctor", rowMap.get(2));
    }
    
    //Range居然还是一个Predicate
    public void testRange()
    {
        Range<Integer> numberRange = Range.open(1, 10);
        assertEquals(false, numberRange.contains(10));
        assertEquals(false, numberRange.contains(1));
        
        numberRange = Range.closed(1,10);
        assertEquals(true, numberRange.contains(10));
        assertEquals(true, numberRange.contains(1));
        
        Function<Person, Integer> ageFun = new Function<Person, Integer>()
        {
            @Override
            public Integer apply(Person input)
            {
                return input.getAge();
            }
        };
        
        Predicate<Person> predicate = Predicates.compose(Range.open(1,30), ageFun);
        assertTrue(predicate.apply(new Person("c", "dd", 29, "F")));
    }
    
  //Sorting
    public void testOrder()
    {
        //when the Cityobjects that have the same population 
        //are being sorted, the Orderinginstance will delegate to the secondary comparator. 
//        Ordering<City> secondaryOrdering = Ordering.
//                        from(cityByPopulation).compound(cityByRainfall);
//                        Collections.sort(cities,secondaryOrdering);
        
        
//        Ordering<City> ordering = Ordering.from(cityByPopluation);
//        List<City> topFive = ordering.greatestOf(cityList,5);
//        List<City> bottomThree = ordering.leastOf(cityList,3)
    }
    
    
}

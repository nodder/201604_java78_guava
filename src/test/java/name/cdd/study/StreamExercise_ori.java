package name.cdd.study;


import static java.lang.System.out;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.toList;

import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Stream;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import junit.framework.TestCase;

public class StreamExercise_ori extends TestCase
{
    private List<Employee2> employees;
    
    @Override
    protected void setUp() throws Exception
    {
        employees = mockEmplyees();
    }
    
    private List<Employee2> mockEmplyees()
    {
        List<Employee2> employees = Lists.newArrayList();
        
        employees.add(employee(100, "Chen", Employee2.GENDER.MALE, 8000, 29));
        employees.add(employee(101, "Zhang", Employee2.GENDER.MALE, 11000, 28));
        employees.add(employee(102, "Wang", Employee2.GENDER.FEMALE, 15000, 33));
        employees.add(employee(103, "Mei", Employee2.GENDER.MALE, 7000, 26));
        employees.add(employee(104, "Yang", Employee2.GENDER.MALE, 21000, 38));
        employees.add(employee(105, "Sun", Employee2.GENDER.FEMALE, 9000, 37));
        employees.add(employee(106, "Fu", Employee2.GENDER.FEMALE, 19000, 32));
        employees.add(employee(107, "Fan", Employee2.GENDER.MALE, 8100, 30));
        employees.add(employee(108, "Shi", Employee2.GENDER.MALE, 22000, 41));
        employees.add(employee(109, "Liang", Employee2.GENDER.FEMALE, 13000, 25));
        
        return employees;
    }
    
    //ϰ��1���г�нˮ����20000��Ա�����֡�//TODOע��
    public void test1()
    {
        List<String> employeeNames = list_employeeNames_salary_largerThan_20000(employees.stream());
        print(employeeNames);
        assertEquals(Lists.newArrayList("Yang", "Shi"), employeeNames);
    }
    
    //ϰ��2��ͬ�����г�нˮ����20000��Ա�����֣���ϰ���еķ�������ֵ��ΪString
    public void test2()
    {
        String employeeNames = list_employeeNames_salary_largerThan_20000_return_string(employees.stream());
        print(employeeNames);
        assertEquals("Yang, Shi", employeeNames);
    }
    
    //ϰ��3���г���ŮԱ��������
    public void test3()
    {
        Map<Employee2.GENDER, Long> employeeNumberOfGender = list_employeeNumber_Of_each_gender(employees.stream());
        
        print(employeeNumberOfGender);
        Map<Employee2.GENDER, Long> expected = Maps.newLinkedHashMap();
        expected.put(Employee2.GENDER.MALE, 6L);
        expected.put(Employee2.GENDER.FEMALE, 4L);
        
        assertEquals(expected, employeeNumberOfGender);
    }
    
    //ϰ��4��������Σ�20~29, 30~39, 40~49��Ա��нˮƽ��ֵ
    public void test4()
    {
        Map<String, Double> averSalaryOfAgePeriod =  list_averageSalary_of_each_agePeriod(employees.stream());
        
        print(averSalaryOfAgePeriod);
        Map<String, Double> expected = Maps.newLinkedHashMap();
        expected.put("20~29", 9750d);
        expected.put("30~39", 14420d);
        expected.put("40~49", 22000d);
        
        assertEquals(expected, averSalaryOfAgePeriod);
    }
    
    //ϰ��5��������Ա�������/���/ƽ��/���ʡ�Ҫ��ֻ�ܹ���һ��stream��
    public void test5()
    {
        //TODO
        IntSummaryStatistics salarySummary = employees.stream().collect(mapping(Employee2::getSalary, summarizingInt(s -> s)));
        System.out.println("��͹���:" + salarySummary.getMin());
        System.out.println("��߹���:" + salarySummary.getMax());
        System.out.println("ƽ������:" + salarySummary.getAverage());
    }
    
    private List<String> list_employeeNames_salary_largerThan_20000(Stream<Employee2> employees)
    {
        //TODO ��ʹ��������ֱ�ӷ��ؽ��
        return employees.filter(e -> e.getSalary() > 20000).map(Employee2::getName).collect(toList());
    }
    
    private String list_employeeNames_salary_largerThan_20000_return_string(Stream<Employee2> employees)
    {   
        //TODO ��ʹ��������ֱ�ӷ��ؽ��
//        return employees.stream().filter(e -> e.getSalary() > 20000).collect(mapping(Employee::getName, joining(", ")));
        return employees.filter(e -> e.getSalary() > 20000).map(Employee2::getName).reduce((x, y) -> x + ", " + y).get();
    }
    
    private Map<Employee2.GENDER, Long> list_employeeNumber_Of_each_gender(Stream<Employee2> employees)
    {
      //TODO ��ʹ��������ֱ�ӷ��ؽ������ʾ��groupingBy
        return employees.collect(groupingBy(Employee2::getGender, counting()));
    }

    private Map<String, Double> list_averageSalary_of_each_agePeriod(Stream<Employee2> employees)
    {
        //TODO  ��ʹ��������ֱ�ӷ��ؽ������ʾ��groupingBy
        //ע�⣺treeMap��֤���ؽ������
        return employees.collect(groupingBy((Employee2 e) -> agePeriod(e.getAge()), TreeMap::new, averagingInt(Employee2::getSalary)));
    }
    
    
    private String agePeriod(int age)
    {
        final int decade = age/10;
        
        return decade * 10 + "~" + ((decade + 1) * 10 - 1);
   }

    private <T, F> void print(Map<T, F> map)
    {
        out.println(toString(map));
    }

    private <T> void print(List<T> list)
    {
        out.println(toString(list));
    }
    
    private void print(String str)
    {
        out.println(str);
    }
    
    private <T> String toString(List<T> list)
    {
        return Joiner.on(", ").join(list);
    }
    
    private <T, F> String toString(Map<T, F> map)
    {
        Iterator<Entry<T, F>> it = map.entrySet().iterator();
        
        String result = "";
        while(it.hasNext())
        {
            Entry<T, F> entry = it.next();
            result += entry.getKey() + ": " + entry.getValue() + "; ";
        }
        return result;
    }
    

    private Employee2 employee(int id, String name, Employee2.GENDER gendar,  int salary, int age)
    {
        return new Employee2(id, name, gendar, salary, age);
    }
}

class Employee2
{
    enum GENDER {MALE, FEMALE};
    
    private int id ;
    private String name;
    private GENDER gender;
    private int salary;
    private int age;
    
    public Employee2(int id, String name, GENDER gender, int salary, int age)
    {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.salary = salary;
        this.age = age;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public GENDER getGender()
    {
        return gender;
    }

    public int getSalary()
    {
        return salary;
    }

    public int getAge()
    {
        return age;
    }
}

package name.cdd.study;

import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.reducing;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.google.common.base.Joiner;

import junit.framework.TestCase;

public class ForPublish extends TestCase
{
    public void test7()
    {
        List<Employee> employees = mockEmplyees();
        
        Map<String, Long> map1 = employees.stream().collect(groupingBy(Employee::getMale, counting()));
        System.out.println("男女员工人数分别：" + print(map1));
        
        
        List<String> emplyeeNames = employees.stream().filter(e -> e.getSalary() > 10000).map(Employee::getName).collect(toList());
        System.out.println("薪水大于10000的员工名字：" + print(emplyeeNames));
        
        String xx = employees.stream().filter(e -> e.getSalary() > 10000).collect(mapping(Employee::getName, joining(", ")));
        System.out.println("薪水大于10000的员工名字：" + xx);
        
        String yyy = employees.stream().filter(e -> e.getSalary() > 10000).map(Employee::getName).reduce((x, y) -> x + ", " + y).get();
        System.out.println("薪水大于10000的员工名字：" + yyy);
        
        Map<String, Double> x = employees.stream().collect(groupingBy((Employee e) -> agePeriod(e.getAge()), averagingInt(Employee::getSalary)));
        System.out.println("各年龄段员工薪水平均值：" + print(x));
        
        
        //排序
        Map<String, Double> y = employees.stream().collect(groupingBy((Employee e) -> agePeriod(e.getAge()), TreeMap::new, averagingInt(Employee::getSalary)));
        System.out.println("各年龄段员工薪水平均值：" + print(y));
        
        
        IntSummaryStatistics salarySummary = employees.stream().collect(mapping(Employee::getSalary, summarizingInt(s -> s)));
        System.out.println("最低工资:" + salarySummary.getMin()) ;
        System.out.println("最高工资:" + salarySummary.getMax()) ;
        System.out.println("平均工资:" + salarySummary.getAverage()) ;
        System.out.println("员工数:" + salarySummary.getCount()) ;
        
        
        Integer xxx = employees.stream().collect(reducing(0, Employee::getSalary, Integer::sum));
        System.out.println(xxx);
    }
    

    private String agePeriod(int age)
    {
        final int decade = age/10;
        
        return decade * 10 + "~" + (decade + 1) * 10;
   }

    private <T, F> String print(Map<T, F> map)
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
    
    private <T> String print(List<T> list)
    {
        return Joiner.on(", ").join(list);
    }

    private List<Employee> mockEmplyees()
    {
        List<Employee> employees = new ArrayList<>();
        employees.add(employee(100, "Chen", "male", 8000, 29));
        employees.add(employee(101, "Zhang", "male", 11000, 28));
        employees.add(employee(102, "Wang", "female", 15000, 33));
        employees.add(employee(103, "Mei", "male", 7000, 26));
        employees.add(employee(104, "Yang", "male", 21000, 38));
        employees.add(employee(105, "Sun", "female", 9000, 37));
        employees.add(employee(106, "Fu", "female", 19000, 32));
        employees.add(employee(107, "Fan", "male", 8100, 30));
        employees.add(employee(108, "Shi", "male", 22000, 41));
        employees.add(employee(109, "Liang", "female", 13000, 25));
        
        return employees;
    }
    
    private Employee employee(int id, String name,String male,  int salary, int age)
    {
        return new Employee(id, name, male, salary, age);
    }

//    class Employee
//    {
//        private int id ;
//        private String name;
//        private String male; //male, female
//        private int salary;
//        private int age;
//        
//        public Employee(int id, String name,String male,  int salary, int age)
//        {
//            this.id = id;
//            this.name = name;
//            this.male = male;
//            this.salary = salary;
//            this.age = age;
//        }
//
//        public int getId()
//        {
//            return id;
//        }
//
//        public void setId(int id)
//        {
//            this.id = id;
//        }
//
//        public String getName()
//        {
//            return name;
//        }
//
//        public void setName(String name)
//        {
//            this.name = name;
//        }
//
//        public String getMale()
//        {
//            return male;
//        }
//
//        public void setMale(String male)
//        {
//            this.male = male;
//        }
//
//        public int getSalary()
//        {
//            return salary;
//        }
//
//        public void setSalary(int salary)
//        {
//            this.salary = salary;
//        }
//
//        public int getAge()
//        {
//            return age;
//        }
//
//        public void setAge(int age)
//        {
//            this.age = age;
//        }
//    }
}

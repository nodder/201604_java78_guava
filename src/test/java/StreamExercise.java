import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import junit.framework.TestCase;

public class StreamExercise extends TestCase
{
    private List<Employee> employees;
    
    @Override
    protected void setUp() throws Exception
    {
        employees = mockEmplyees();
    }
    
    private List<Employee> mockEmplyees()
    {
        List<Employee> employees = new ArrayList<>();
        
        employees.add(employee(100, "Chen", Employee.GENDER.MALE, 8000, 29));
        employees.add(employee(101, "Zhang", Employee.GENDER.MALE, 11000, 28));
        employees.add(employee(102, "Wang", Employee.GENDER.FEMALE, 15000, 33));
        employees.add(employee(103, "Mei", Employee.GENDER.MALE, 7000, 26));
        employees.add(employee(104, "Yang", Employee.GENDER.MALE, 21000, 38));
        employees.add(employee(105, "Sun", Employee.GENDER.FEMALE, 9000, 37));
        employees.add(employee(106, "Fu", Employee.GENDER.FEMALE, 19000, 32));
        employees.add(employee(107, "Fan", Employee.GENDER.MALE, 8100, 30));
        employees.add(employee(108, "Shi", Employee.GENDER.MALE, 22000, 41));
        employees.add(employee(109, "Liang", Employee.GENDER.FEMALE, 13000, 25));
        
        return employees;
    }
    
    //习题1：列出薪水大于20000的员工名字。
    public void test1()
    {
        List<String> employeeNames = list_employeeNames_salary_largerThan_20000(employees.stream());
        assertEquals(list("Yang", "Shi"), employeeNames);
    }
    
    //习题2：同样是列出薪水大于20000的员工名字，但习题中的方法返回值改为String
    public void test2()
    {
        String employeeNames = list_employeeNames_salary_largerThan_20000_return_string(employees.stream());
        assertEquals("Yang, Shi", employeeNames);
    }
    
    //习题3：列出男女员工的人数
    public void test3()
    {
        Map<Employee.GENDER, Long> employeeNumberOfGender = list_employeeNumber_Of_each_gender(employees.stream());
        
        Map<Employee.GENDER, Long> expected = new LinkedHashMap<>();
        expected.put(Employee.GENDER.MALE, 6L);
        expected.put(Employee.GENDER.FEMALE, 4L);
        
        assertEquals(expected, employeeNumberOfGender);
    }
    
    //习题4：求年龄段（20~29, 30~39, 40~49）员工薪水平均值
    public void test4()
    {
        Map<String, Double> averSalaryOfAgePeriod =  list_averageSalary_of_each_agePeriod(employees.stream());
        
        Map<String, Double> expected = new LinkedHashMap<>();
        expected.put("20~29", 9750d);
        expected.put("30~39", 14420d);
        expected.put("40~49", 22000d);
        
        assertEquals(expected, averSalaryOfAgePeriod);
    }
    
    //习题5：求所有员工中最低/最高/平均/工资。要求只能构造一次stream。
    public void test5()
    {
        //TODO
    }
    
    private List<String> list_employeeNames_salary_largerThan_20000(Stream<Employee> employees)
    {
        //TODO 请使用流运算直接返回结果
        return null;
    }
    
    private String list_employeeNames_salary_largerThan_20000_return_string(Stream<Employee> employees)
    {   
        //TODO 请使用流运算直接返回结果
        return null;
    }
    
    private Map<Employee.GENDER, Long> list_employeeNumber_Of_each_gender(Stream<Employee> employees)
    {
      //TODO 请使用流运算直接返回结果。提示：groupingBy
        return null;
    }

    private Map<String, Double> list_averageSalary_of_each_agePeriod(Stream<Employee> employees)
    {
        //TODO  请使用流运算直接返回结果。提示：groupingBy
        //注意：treeMap保证返回结果有序。
        return null;
    }
    
    private String agePeriod(int age)
    {
        final int decade = age/10;
        
        return decade * 10 + "~" + ((decade + 1) * 10 - 1);
    }

    private Employee employee(int id, String name, Employee.GENDER gendar,  int salary, int age)
    {
        return new Employee(id, name, gendar, salary, age);
    }
    
    @SuppressWarnings ("unchecked")
    private <T> List<T> list(T... t)
    {
        return Arrays.asList(t);
    }
}

class Employee
{
    enum GENDER {MALE, FEMALE};
    
    private int id ;
    private String name;
    private GENDER gender;
    private int salary;
    private int age;
    
    public Employee(int id, String name, GENDER gender, int salary, int age)
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

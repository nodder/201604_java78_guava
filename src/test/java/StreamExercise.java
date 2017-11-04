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
    
    //ϰ��1���г�нˮ����20000��Ա�����֡�
    public void test1()
    {
        List<String> employeeNames = list_employeeNames_salary_largerThan_20000(employees.stream());
        assertEquals(list("Yang", "Shi"), employeeNames);
    }
    
    //ϰ��2��ͬ�����г�нˮ����20000��Ա�����֣���ϰ���еķ�������ֵ��ΪString
    public void test2()
    {
        String employeeNames = list_employeeNames_salary_largerThan_20000_return_string(employees.stream());
        assertEquals("Yang, Shi", employeeNames);
    }
    
    //ϰ��3���г���ŮԱ��������
    public void test3()
    {
        Map<Employee.GENDER, Long> employeeNumberOfGender = list_employeeNumber_Of_each_gender(employees.stream());
        
        Map<Employee.GENDER, Long> expected = new LinkedHashMap<>();
        expected.put(Employee.GENDER.MALE, 6L);
        expected.put(Employee.GENDER.FEMALE, 4L);
        
        assertEquals(expected, employeeNumberOfGender);
    }
    
    //ϰ��4��������Σ�20~29, 30~39, 40~49��Ա��нˮƽ��ֵ
    public void test4()
    {
        Map<String, Double> averSalaryOfAgePeriod =  list_averageSalary_of_each_agePeriod(employees.stream());
        
        Map<String, Double> expected = new LinkedHashMap<>();
        expected.put("20~29", 9750d);
        expected.put("30~39", 14420d);
        expected.put("40~49", 22000d);
        
        assertEquals(expected, averSalaryOfAgePeriod);
    }
    
    //ϰ��5��������Ա�������/���/ƽ��/���ʡ�Ҫ��ֻ�ܹ���һ��stream��
    public void test5()
    {
        //TODO
    }
    
    private List<String> list_employeeNames_salary_largerThan_20000(Stream<Employee> employees)
    {
        //TODO ��ʹ��������ֱ�ӷ��ؽ��
        return null;
    }
    
    private String list_employeeNames_salary_largerThan_20000_return_string(Stream<Employee> employees)
    {   
        //TODO ��ʹ��������ֱ�ӷ��ؽ��
        return null;
    }
    
    private Map<Employee.GENDER, Long> list_employeeNumber_Of_each_gender(Stream<Employee> employees)
    {
      //TODO ��ʹ��������ֱ�ӷ��ؽ������ʾ��groupingBy
        return null;
    }

    private Map<String, Double> list_averageSalary_of_each_agePeriod(Stream<Employee> employees)
    {
        //TODO  ��ʹ��������ֱ�ӷ��ؽ������ʾ��groupingBy
        //ע�⣺treeMap��֤���ؽ������
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

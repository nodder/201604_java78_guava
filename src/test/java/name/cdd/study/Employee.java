package name.cdd.study;

public class Employee
{
    private int id ;
    private String name;
    private String male; //male, female
    private int salary;
    private int age;
    
    public Employee(int id, String name,String male,  int salary, int age)
    {
        this.id = id;
        this.name = name;
        this.male = male;
        this.salary = salary;
        this.age = age;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMale()
    {
        return male;
    }

    public void setMale(String male)
    {
        this.male = male;
    }

    public int getSalary()
    {
        return salary;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }
}
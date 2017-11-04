package name.cdd.study.guava;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class Person implements Comparable<Person>
{
    private String firstName;
    private String lastName;
    private int age;
    private String sex;
    
    public Person(String firstName, String lastName, int age, String sex)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    public String getLastName()
    {
        return lastName;
    }
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    public int getAge()
    {
        return age;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
    public String getSex()
    {
        return sex;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(firstName, lastName, sex, age);
    }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        
        if(obj == null)
            return false;
        
        
        if(getClass() != obj.getClass())
            return false;
        
        return Objects.equal(firstName, ((Person)obj).firstName)
            && Objects.equal(lastName, ((Person)obj).lastName)
            && (age == ((Person)obj).age)
            && Objects.equal(sex, ((Person)obj).sex);
    }

    @Override
    public int compareTo(Person o)
    {//注意这个写法!
        return ComparisonChain.start().
            compare(this.firstName,o.getFirstName()).
            compare(this.lastName,o.getLastName()).
            compare(this.age,o.getAge()).
            compare(this.sex,o.getSex()).result();
    }
    
    
}

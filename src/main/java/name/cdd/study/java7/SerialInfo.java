package name.cdd.study.java7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class SerialInfo implements Serializable
{
    private static final long serialVersionUID = 4789242313659318987L;

    private int age;
    private String name;
    
    public SerialInfo(String name, int age)
    {   
        this.name = name;
        this.age = age;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }
    
    private void writeObject(ObjectOutputStream output) throws IOException
    {
        output.defaultWriteObject();
    }
    
    private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException
    {
        input.defaultReadObject();
        this.age = 199;
//        this.name = "Zhang";
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
        
        return (this.age == ((SerialInfo)obj).getAge() && Objects.equals(this.name, ((SerialInfo)obj).getName()));
    }
    
    
}

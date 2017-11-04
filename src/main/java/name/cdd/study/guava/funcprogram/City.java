package name.cdd.study.guava.funcprogram;

import com.google.common.base.Objects;

public class City
{
    private String name;
    private String zipCode;
    private int population;
    private float averageRainfall;

    public City(String name, String zipCode, int population, float averageRainfall)
    {
        this.name = name;
        this.zipCode = zipCode;
        this.population = population;
        this.averageRainfall = averageRainfall;
    }

    public String getName()
    {
        return name;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public int getPopulation()
    {
        return population;
    }
    
    public float getAverageRainfall()
    {
        return averageRainfall;
    }

    public void setAverageRainfall(float averageRainfall)
    {
        this.averageRainfall = averageRainfall;
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
        
        return Objects.equal(name, ((City)obj).name);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hashCode(name);
    }

    @Override
    public String toString()
    {
        return name;
    }
}

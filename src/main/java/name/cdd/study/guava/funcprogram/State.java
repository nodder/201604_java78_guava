package name.cdd.study.guava.funcprogram;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Objects;

public class State
{
    private String name;
    private String code;
    private Set<City> mainCities = new HashSet<City>();
    
    public State(String name, String code, HashSet<City> mainCities)
    {
        this.name= name;
        this.code = code;
        this.mainCities = mainCities;
    }

    public String getName()
    {
        return name;
    }

    public String getCode()
    {
        return code;
    }

    public Set<City> getMainCities()
    {
        return mainCities;
    }

    @SuppressWarnings ("deprecation")
    @Override
    public String toString()
    {
        return Objects.toStringHelper(this).omitNullValues()
                        .add("name", name)
                        .add("code", code)
                        .add("mainCities", mainCities).toString();
    }
}

package name.cdd.study.guava.funcprogram;

import com.google.common.base.Predicate;

public class LowRainfallPredicate implements Predicate<City>
{
    @Override
    public boolean apply(City input)
    {
        return input.getAverageRainfall() < 45.7;
    }
    
//    @Override
//    public boolean apply(City input)
//    {
//        return input.getAverageRainfall() < 45.7;
//    }

}

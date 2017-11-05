package name.cdd.study.guava.funcprogram;

import com.google.common.base.Predicate;

public class PopulationPredicate implements Predicate<City>
{
    @Override
    public boolean apply(City input)
    { 
        return input.getPopulation() <= 500000;
    }
    
}

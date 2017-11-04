package name.cdd.study.java7.guava.funcprogram;

import java.util.HashMap;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import junit.framework.TestCase;
import name.cdd.study.guava.funcprogram.City;
import name.cdd.study.guava.funcprogram.LowRainfallPredicate;
import name.cdd.study.guava.funcprogram.PopulationPredicate;
import name.cdd.study.guava.funcprogram.State;

public class PredicateTest extends TestCase
{
    //Predicates还有or, contains, compose等众多方法，提供了各种控制条件的灵活组合。
    public void testPredicateApply()
    {
        Predicate<City> composedPredicates = Predicates.and(new LowRainfallPredicate(), new PopulationPredicate());
        
        City cityTx = new City("Austin,TX", "12345", 250000, 20.0f);
        City cityNy = new City("New York,NY", "12345", 2000000, 20.f);
        
        assertEquals(true, composedPredicates.apply(cityTx));
        assertEquals(false, composedPredicates.apply(cityNy));
    }
    
    public void testPredicateCompose()
    {
        Function<String, State> stateMap = makeStateMap();
        Predicate<String> isStateContainsBuffaloPredicate = Predicates.compose(new CityBuffaloBelongsPredicate(), stateMap);
        
        assertEquals(false, isStateContainsBuffaloPredicate.apply("TX"));
        assertEquals(true, isStateContainsBuffaloPredicate.apply("NY"));
    }
    
    //memoize是提供单例的一种便捷的方法。他还有一个重载方法，还提供了超时时间的参数。
    public void testSuppliers()
    {
        Supplier<Predicate<String>> supplier = Suppliers.memoize(new Supplier<Predicate<String>>()
        {
            @Override
            public Predicate<String> get()
            {
                Function<String, State> stateMap = makeStateMap();
                return Predicates.compose(new CityBuffaloBelongsPredicate(), stateMap);
            }
        });
        
        Predicate<String> isStateContainsBuffaloPredicate = supplier.get();
        
        assertEquals(false, isStateContainsBuffaloPredicate.apply("TX"));
        assertEquals(true, isStateContainsBuffaloPredicate.apply("NY"));
    }
    
    private Function<String, State> makeStateMap()
    {
        City city = new City("Austin,TX","12345",250000, 100f);
        State state = new State("Texas","TX", Sets.newHashSet(city));
        
        City cityN = new City("New York,NY","12345",510000, 100f);
        City cityB = new City("Buffalo","22222",200000, 100f);
        City cityR = new City("Rochester","33333",100000, 100f);
        State state1 = new State("New York","NY",Sets.newHashSet(cityN, cityB, cityR));
        
        HashMap<String,State> stateMap = Maps.newHashMap();
        stateMap.put(state.getCode(), state);
        stateMap.put(state1.getCode(), state1);
        
        return Functions.forMap(stateMap);
    }

    private class CityBuffaloBelongsPredicate implements Predicate<State>
    {
        @Override
        public boolean apply(State input)
        {
            return input.getMainCities().contains(new City("Buffalo","22222",200000, 100f));
        }
        
    }
}

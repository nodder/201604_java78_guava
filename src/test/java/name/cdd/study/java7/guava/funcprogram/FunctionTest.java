package name.cdd.study.java7.guava.funcprogram;

import java.util.HashMap;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import junit.framework.TestCase;
import name.cdd.study.guava.funcprogram.City;
import name.cdd.study.guava.funcprogram.State;
import name.cdd.study.guava.funcprogram.StateToCityString;

public class FunctionTest extends TestCase
{
    //Functions.forMap仅是一种简单的包装，目的是后面灵活的组合（下一个测试用例）。但在这个用例中，体现不出威力。
    public void testFunction()
    {
        Function<String, State> stateMap = makeStateMap();
        
        State state = stateMap.apply("NY");
        
        assertEquals("NY", state.getCode());
    }
    
    public void testFunctionCompose()
    {
        Function<String, State> stateMap = makeStateMap();
        Function<String, String> composed = Functions.compose(new StateToCityString(), stateMap);
        //注：等同于stateFunction.apply(stateMap.apply("NY"));
        
        assertEquals("New York,NY,Buffalo,Rochester", composed.apply("NY"));
    }
    
    private Function<String, State> makeStateMap()
    {
        City city = new City("Austin,TX","12345",250000, 100f);
        State state = new State("Texas","TX", Sets.newLinkedHashSet(Lists.newArrayList(city)));
        
        City cityN = new City("New York,NY","12345",510000, 100f);
        City cityB = new City("Buffalo","22222",200000, 100f);
        City cityR = new City("Rochester","33333",100000, 100f);
        State state1 = new State("New York","NY",Sets.newLinkedHashSet(Lists.newArrayList(cityN, cityB, cityR)));
        
        HashMap<String,State> stateMap = Maps.newHashMap();
        stateMap.put(state.getCode(), state);
        stateMap.put(state1.getCode(), state1);
        
        return Functions.forMap(stateMap);
    }
}

package name.cdd.study.java8;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import junit.framework.TestCase;

@SuppressWarnings ("unused")
public class Chapter1Study extends TestCase
{
    private String[] wordArr = null;
    private List<String> wordList = null;
    
    private Button button = null;
    
    @Override
    protected void setUp() throws Exception
    {
        wordArr = new String[]{"first", "second", "third"};
        wordList = Arrays.asList("first", "second", "third");
    }
    
    public void testChapter1()
    {
        
        Comparator<String> lengthComp = new Comparator<String>()
        {
            @Override
            public int compare(String o1, String o2)
            {
                return Integer.compare(o1.length(), o2.length());
            }
        };
        assertEquals(lengthComp.compare("123", "1234"), -1);
        
        Comparator<String> lengthComp2 = (o1, o2) -> Integer.compare(o1.length(), o2.length()); 
        assertEquals(lengthComp2.compare("123", "1234"), -1);
        
        //简洁，可以多加使用Comparator类
        Comparator<String> lengthComp3 = Comparator.comparing(String::length);
        
        /////////////////////////////////////////////////
        
        EventHandler<ActionEvent> listener = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                System.out.println("in EventHandler handle");
            }
        };
        
        EventHandler<ActionEvent> listener2 = (event) -> System.out.println("in EventHandler handle2");
       
        EventHandler<ActionEvent> listener3 = event -> System.out.println("in EventHandler handle2");
        
        Arrays.sort(wordArr, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        
//        button.setOnAction(event -> System.out.println("in EventHandler handle3"));
//        button.setOnAction(System.out::println);
        
        Callable<Void> sleepter = () -> {System.out.println("in sleep"); Thread.sleep(1000); return null;};
        
        Arrays.sort(wordArr, (s1, s2) -> s1.compareToIgnoreCase(s2));
        Arrays.sort(wordArr, String::compareToIgnoreCase);

        //Math::pow等同于(x, y) -> Math.pow(x, y)
        
        //转为数组
        Stream<Integer> lengthList = wordList.stream().map(String::length);
        Integer[] lengthArr = lengthList.toArray(Integer[]::new);
        
        //forEach是Iterable接口在1.8中新定义的默认方法。
        //如果父类和接口都定义了同名方法，遵循类优先的原则。这是出于兼容性的考虑，确保对于java8以前编写的代码不会产生影响，。
        //如果不同接口都定义的同名方法，则编译出错，由开发人员处理。开发人员可以人工选择调用其中一个接口，或者从头写。P15
        wordList.forEach(System.out::println);
        
    }
    
    class Greeter
    {
        public void greet()
        {
            System.out.println("Hello world.");
        }
    }
    
    class ConcurrentGreeter extends Greeter
    {
        @Override
        public void greet()
        {
            new Thread(() -> super.greet());
            new Thread(super::greet);//两种写法一样
        }
    }
    
    class Application
    {
        public void doWork() { 
        Runnable runner = () -> {System.out.println(this.toString()); };//this调用的是application，而不是Runnable
        }
    }

}

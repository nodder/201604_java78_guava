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
        
        //��࣬���Զ��ʹ��Comparator��
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

        //Math::pow��ͬ��(x, y) -> Math.pow(x, y)
        
        //תΪ����
        Stream<Integer> lengthList = wordList.stream().map(String::length);
        Integer[] lengthArr = lengthList.toArray(Integer[]::new);
        
        //forEach��Iterable�ӿ���1.8���¶����Ĭ�Ϸ�����
        //�������ͽӿڶ�������ͬ����������ѭ�����ȵ�ԭ�����ǳ��ڼ����ԵĿ��ǣ�ȷ������java8��ǰ��д�Ĵ��벻�����Ӱ�죬��
        //�����ͬ�ӿڶ������ͬ�����������������ɿ�����Ա����������Ա�����˹�ѡ���������һ���ӿڣ����ߴ�ͷд��P15
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
            new Thread(super::greet);//����д��һ��
        }
    }
    
    class Application
    {
        public void doWork() { 
        Runnable runner = () -> {System.out.println(this.toString()); };//this���õ���application��������Runnable
        }
    }

}

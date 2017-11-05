package name.cdd.study.java8;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import junit.framework.TestCase;


public class Chapter1 extends TestCase
{
    @Override
    protected void setUp() throws Exception
    {
        new File("testData/chap1_2").mkdirs();
        new File("testData/chap1_2/1.txt").createNewFile();
        new File("testData/chap1_2/2.rar").createNewFile();
        new File("testData/chap1_2/dir1").mkdir();
    }
    
    
    public void test2()
    {
        File[] result = searchDir2(new File("testData/chap1_2"));
        
        assertEquals(1, result.length);
        assertEquals("testData\\chap1_2\\dir1", result[0].toString());
    }
    
    public void test3()
    {
        File file = new File("testData/chap1_2");
        
        String[] result = fileterWithSuffix(file, ".rar");
        
        assertEquals(1, result.length);
        assertEquals("2.rar", result[0]);
    }
    
    public void test7()
    {
        andThen(() -> System.out.println("in thread1"), () -> System.out.println("in thread2"));
    }
    
    public void test8()
    {
        String[] names = {"a", "b", "c"};
        
        List<Runnable> runners = new ArrayList<Runnable>();
        
        for(String name : names)
        {
            runners.add(() -> System.out.println(name));
        }
        
        //如果使用下面这种，就会编译出错。
//        for(int i = 0; i < names.length; i++)
//          {
//              runners.add(() -> System.out.println(names[i]));
//          }
        
        runners.forEach(r -> r.run());
    }
    
    public void test9()
    {
        Collection2<String> list = new ArrayList2<String>();
        list.addAll(Arrays.asList("aa", "bb", "cccc", "dd"));
        
        list.forEachIf(System.out::println, s -> s.length() >2);
    }
    
    private Runnable andThen(Runnable r1, Runnable r2)
    {
       return () -> {r1.run(); r2.run();};

      //也可以用这种
//        return new Runnable()
//        {
//            @Override
//            public void run()
//            {
//                r1.run();
//                r2.run();
//            }
//        };
    }
    
    
    private String[] fileterWithSuffix(File file, String suffix)
    {
        return file.list((dir, name) -> name.toLowerCase().endsWith(suffix.toLowerCase()));
    }


    @SuppressWarnings ("unused")
    private File[] searchDir(File file)
    {
        return file.listFiles(new FileFilter()
        {
            @Override
            public boolean accept(File tmpFile)
            {
                return tmpFile.isDirectory();
            }
        });
    }
    
    private File[] searchDir2(File file)
    {
        return file.listFiles(f -> f.isDirectory());
    }
    
    interface Collection2<T> extends Collection<T>
    {
        default void forEachIf(Consumer<T> action, Predicate<T> filter)
        {
            for(T t : this)
            {
                if(filter.test(t))
                {
                    action.accept(t);
                }
            }
        }
    }
    
    class ArrayList2<T> extends ArrayList<T> implements Collection2<T>
    {
        private static final long serialVersionUID = 1L;
    }
}

package name.cdd.study.java7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import junit.framework.TestCase;

public class NewFeatureTest extends TestCase
{
    Logger logger = null;
    
    public void test_int()
    {
        int x = 0b11111111;
        int y = 0xff;
        int z = 255;
        int m = 0377;

        assertEquals(x, y);
        assertEquals(y, z);
        assertEquals(y, z);
        assertEquals(z, m);
        
        assertEquals(5, 0b00101);
        
        //下划线用来分隔数字，方便阅读。注意只能插入在数字中间。
        assertEquals(1000000, 1_000_000);
        assertEquals(56.34, 5_6.3_4);
    }
    
    public void test_catch()
    {
        //同一个catch包含的多个异常，不能互为子类，否则出编译错误。
        //Java7核心技术与最佳时间 P15，写错了。实际上下面这段代码不能通过编译，
//        try
//        {
//            Integer.parseInt("Hello");
//        }
//        catch(NumberFormatException | RuntimeException e)
//        {
//            
//        }
       
         
          try
          {
              doSomething();
          }
          catch(NumberFormatException |  IndexOutOfBoundsException e)
          {
              assertTrue(e instanceof NumberFormatException);
          }
    }
    
    private void doSomething()
    {
        // TODO Auto-generated method stub
        
    }

    //能够被try语句所管理的资源需要java类实现java.lang.AutoCloseable接口。这个接口定义于JDK1.7.
    //数据库相关的Connection, ResultSet, Statement继承了该接口（JDK1.7及以上版本）
    public void test_try_with_resources() throws IOException
    {
        prepareFile("a.txt");
        
        //输出：on FileReaderStub close()
        //     on BufferedReaderStub close()
        try(BufferedReaderStub brStub = new BufferedReaderStub(new FileReaderStub("a.txt")))
        {}
        
        //输出：on FileReaderStub close()
        //     on FileReaderStub close()
        try(FileReaderStub fs1 = new FileReaderStub("a.txt");
            FileReaderStub fs2 = new FileReaderStub("a.txt"))
        {}
    }
    
    
    private void prepareFile(String fileName) throws IOException
    {
        try(FileWriter fw = new FileWriter(new File(fileName))){};
    }
    
    private class BufferedReaderStub extends BufferedReader
    {
        public BufferedReaderStub(Reader in)
        {
            super(in);
        }

        @Override
        public void close() throws IOException
        {
            super.close();
            System.out.println("on BufferedReaderStub close()");
        }
    }
    
    private class FileReaderStub extends FileReader
    {
        public FileReaderStub(String fileName) throws FileNotFoundException
        {
            super(fileName);
        }

        @Override
        public void close() throws IOException
        {
            super.close();
            System.out.println("on FileReaderStub close()");
        }
    }
}

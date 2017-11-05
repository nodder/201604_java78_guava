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
        
        //�»��������ָ����֣������Ķ���ע��ֻ�ܲ����������м䡣
        assertEquals(1000000, 1_000_000);
        assertEquals(56.34, 5_6.3_4);
    }
    
    public void test_catch()
    {
        //ͬһ��catch�����Ķ���쳣�����ܻ�Ϊ���࣬������������
        //Java7���ļ��������ʱ�� P15��д���ˡ�ʵ����������δ��벻��ͨ�����룬
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

    //�ܹ���try������������Դ��Ҫjava��ʵ��java.lang.AutoCloseable�ӿڡ�����ӿڶ�����JDK1.7.
    //���ݿ���ص�Connection, ResultSet, Statement�̳��˸ýӿڣ�JDK1.7�����ϰ汾��
    public void test_try_with_resources() throws IOException
    {
        prepareFile("a.txt");
        
        //�����on FileReaderStub close()
        //     on BufferedReaderStub close()
        try(BufferedReaderStub brStub = new BufferedReaderStub(new FileReaderStub("a.txt")))
        {}
        
        //�����on FileReaderStub close()
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

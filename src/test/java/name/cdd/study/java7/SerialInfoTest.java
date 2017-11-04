package name.cdd.study.java7;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import junit.framework.TestCase;

public class SerialInfoTest extends TestCase
{
    public void testSerial() throws IOException, ClassNotFoundException
    {
        SerialInfo info = new SerialInfo("Tony", 18);
        write(info);
        assertEquals(new SerialInfo("Tony", 199), read());
        assertNotSame(info, read());
    }
    
    private static void write(SerialInfo info) throws IOException
    {
        Path path = Paths.get("serial.obj");
        
        try(ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(path)))
        {
            output.writeObject(info);
        }
    }
    
    private static SerialInfo read() throws IOException, ClassNotFoundException
    {
        Path path = Paths.get("serial.obj");
        
        try(ObjectInputStream input = new ObjectInputStream(Files.newInputStream(path)))
        {
            return (SerialInfo)input.readObject();
        }
    }
    
    /*********************** 加密序列化   
     * @throws InvalidKeyException **************/
    
    public void testSealedSerial() throws IOException, ClassNotFoundException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
    {
        SerialInfo info = new SerialInfo("Tony", 18);
        Cipher cp = getCipher();
        writeSafely(info, cp);
        
        //不会从加密中读取，下面的代码不能正常工作。
//        assertEquals(info, read(cp));
        
//        assertEquals( new SerialInfo("Tony", 199), read());
//        assertNotSame(info, read());
    }

    private Cipher getCipher() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException
    {
        byte[] keyData = readBytes();
        SecretKeySpec keySpec = new SecretKeySpec(keyData, "DES");
        Cipher cp = Cipher.getInstance("DES");
        cp.init(Cipher.DECRYPT_MODE, keySpec);
        return cp;
    }
    
    private byte[] readBytes()
    {
        return new byte[] {0, 0, 0, 0, 0, 0, 0, 0};
    }

    private static void writeSafely(SerialInfo info, Cipher cp) throws IOException
    {
        Path path = Paths.get("SealedSerail.obj");
        
        try(ObjectOutputStream output = new ObjectOutputStream(
            new CipherOutputStream(Files.newOutputStream(path), cp)))
        {
            output.writeObject(info);
        }
    }
    
//    private static SerialInfo read(Cipher cp) throws IOException, ClassNotFoundException
//    {
//        Path path = Paths.get("SealedSerail.obj");
//        
//        try(ObjectInputStream input = new ObjectInputStream(
//            new CipherInputStream(Files.newInputStream(path), cp)))
//        {
//            return (SerialInfo)input.readObject();
//        }
//    }
}

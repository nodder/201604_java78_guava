package name.cdd.study.java8;

import java.awt.Color;
import java.awt.Image;
import java.util.List;
import java.util.function.UnaryOperator;

import junit.framework.TestCase;

@SuppressWarnings ("unused")
public class Chapter3Study extends TestCase
{
    //����UnaryOperator�������
    //�ӳټ���
    public void testCompose()
    {
        assertEquals(202, transform(100, compose(x -> x + 1, x -> x * 2)).intValue());
    }
    
    private <T> T transform(T t, UnaryOperator<T> oper)
    {
        return oper.apply(t);
    }
    
    private <T> UnaryOperator<T> compose(UnaryOperator<T> op1, UnaryOperator<T> op2)
    {
        return t -> op2.apply(op1.apply(t));
    }
    
    public void testLatent()
    {
        Image imfrom = null;
        Image result = LatentImage.from(imfrom).transform(Color::brighter).transform(Color::brighter).toImage();
        
        //��ȡcpu������
        Runtime.getRuntime().availableProcessors();
    }
}

class LatentImage 
{
    private Image in;
    private List<UnaryOperator<Color>> pendingOperations;
    
    private LatentImage(Image image)
    {
        this.in = image;
    }
    
    public static LatentImage from(Image image)
    {
        return new LatentImage(image);
    }
    
    public LatentImage transform(UnaryOperator<Color> oper)
    {
        pendingOperations.add(oper);
        return this;
    }
    
    //��һ���սắ��
    public Image toImage()
    {
        //����д����UnaryOperator��Ӧ��
        return null;
    }
}

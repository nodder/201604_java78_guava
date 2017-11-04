package name.cdd.study.guava;

import java.util.Set;

import com.google.common.base.Optional;

import junit.framework.TestCase;

//Optional.of(T)�����һ��Optional�������ڲ�������һ����null��T��������ʵ������T=null�������̱���
//Optional.absent()�����һ��Optional�������ڲ������˿�ֵ
//Optional.fromNullable(T)����һ��T��ʵ��ת��ΪOptional����T��ʵ�����Բ�Ϊ�գ�Ҳ����Ϊ��[Optional.fromNullable(null)����Optional.absent()�ȼۡ�
//boolean isPresent()�����Optional������Tʵ����Ϊnull���򷵻�true����Tʵ��Ϊnull������false
//
//T get()������Optional������Tʵ������Tʵ�����벻Ϊ�գ����򣬶԰���null��Optionalʵ������get()���׳�һ��IllegalStateException�쳣
//T or(T)����Optionalʵ���а����˴����T����ͬʵ��������Optional�����ĸ�Tʵ�������򷵻������Tʵ����ΪĬ��ֵ
//T orNull()������Optionalʵ���а����ķǿ�Tʵ�������Optional�а������ǿ�ֵ������null���������fromNullable()
//Set<T> asSet()������һ�������޸ĵ�Set����Set�а���Optionalʵ���а��������зǿմ��ڵ�Tʵ�������ڸ�Set�У�ÿ��Tʵ�����ǵ�̬�����Optional��û�зǿմ��ڵ�Tʵ�������صĽ���һ���յĲ����޸ĵ�Set��
//�ο���http://www.cnblogs.com/peida/archive/2013/06/14/Guava_Optional.html
public class OptionalTest extends TestCase
{
    //������δ���Ϊ��ʾ���룬����չʾOptional���ǿ���û�˼������ֵ��Ϊnull�ĺ���ʹ�������
    public void testOptional()
    {
        Optional<Long> value = method();
        
        if(value.isPresent() == true)
        {
            System.out.println("��÷���ֵ: " + value.get());
        }
        else
        {

            System.out.println("��÷���ֵ: " + value.or(-12L));
        }

        System.out.println("��÷���ֵ orNull: " + value.orNull());

        Optional<Long> valueNoNull = methodNoNull();
        if(valueNoNull.isPresent() == true)
        {
            Set<Long> set = valueNoNull.asSet();
            System.out.println("��÷���ֵ set �� size : " + set.size());
            System.out.println("��÷���ֵ: " + valueNoNull.get());
        }
        else
        {
            System.out.println("��÷���ֵ: " + valueNoNull.or(-12L));
        }

        System.out.println("��÷���ֵ orNull: " + valueNoNull.orNull());
    }

    private Optional<Long> method()
    {
        return Optional.fromNullable(null);
    }

    private Optional<Long> methodNoNull()
    {
        return Optional.fromNullable(15L);
    }
}

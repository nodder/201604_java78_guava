package name.cdd.study.java8;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import junit.framework.TestCase;

public class Chapter5_time extends TestCase
{
    public void testInstant() throws InterruptedException
    {
        Instant start = Instant.now();
        Thread.sleep(1123);
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);
        System.out.println(timeElapsed.getSeconds());
        System.out.println(timeElapsed.getNano());
        System.out.println(timeElapsed.toNanos());
        System.out.println(timeElapsed.toMillis());
//        timeElapsed.plusMinutes(100);//��ѧ����
    }
        
    public void testLocalDate()
    {
        LocalDate now = LocalDate.now();
        System.out.println(now);
        
        LocalDate day = LocalDate.of(2016, 5, 5);
        System.out.println(day);
        
        LocalDate day2 = LocalDate.of(2016, Month.MAY, 4);
        day2.plusDays(1);
        System.out.println(day2);
        
        //LocalDate��Period��Ӧ��Instant��Duration��Ӧ
        Period period = day.until(LocalDate.of(2016, 6, 7));
        System.out.println(period.getDays());
        System.out.println(period.getMonths());
    }
    
    public void testTemporalAdjuster()
    {
        LocalDate day = LocalDate.of(2016, 5, 1);
        LocalDate saturdayAfterDay = day.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));//ָ������֮���������
        System.out.println(saturdayAfterDay);
    }
    
    public void testTemporalAdjuster_workday()
    {
        TemporalAdjuster nextWorkDay = temporal -> {
            LocalDate result = ((LocalDate)temporal);
            do
            {
                result = result.plusDays(1);
            }
            while(result.getDayOfWeek().getValue() >= 6);
            
            return result;
        };
        
        LocalDate day = LocalDate.of(2016, 5, 7);//����
        LocalDate nextWordDay = day.with(nextWorkDay);
        assertEquals(LocalDate.of(2016, 5, 9), nextWordDay);
    }
    
    //���Ա����ϸ������е�ǿת����
    public void testTemporalAdjuster_workday2()
    {
        TemporalAdjuster nextWorkDay = TemporalAdjusters.ofDateAdjuster(
            date -> {
                LocalDate result = date;
                do
                {
                    result = result.plusDays(1);
                }
                while(result.getDayOfWeek().getValue() >= 6);
                
                return result;
            });
        
        LocalDate day = LocalDate.of(2016, 5, 7);//����
        LocalDate nextWordDay = day.with(nextWorkDay);
        assertEquals(LocalDate.of(2016, 5, 9), nextWordDay);
    }
    
    public void testLocalTime()
    {
        Instant instant = Instant.now();//��long��ʾ��1970-1-1 00:00:00�����ڵ�nanosecond����ӡ�����LocalDateTime���
        System.out.println(instant);
        
        LocalTime time = LocalTime.now();//LocalTimeΪһ���е�ĳ��ʱ��
        System.out.println(time);
        
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);//��ӡ��Instant��ࡣ ��ʾ����ʱ�䣬���Ҫ��ʱ����������ZonedDateTime
        
        ZonedDateTime zoneddateTime = ZonedDateTime.now();
        System.out.println(zoneddateTime);
    }
    
    public void testDateTimeFormatter()
    {
        System.out.println("=========== testDateTimeFormatter ==================");
        String nowTime = DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now());
        System.out.println(nowTime);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(formatter.format(LocalDateTime.now()));
    }
    
}

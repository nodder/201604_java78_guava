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
//        timeElapsed.plusMinutes(100);//数学操作
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
        
        //LocalDate和Period对应；Instant和Duration对应
        Period period = day.until(LocalDate.of(2016, 6, 7));
        System.out.println(period.getDays());
        System.out.println(period.getMonths());
    }
    
    public void testTemporalAdjuster()
    {
        LocalDate day = LocalDate.of(2016, 5, 1);
        LocalDate saturdayAfterDay = day.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));//指定日期之后的星期六
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
        
        LocalDate day = LocalDate.of(2016, 5, 7);//周六
        LocalDate nextWordDay = day.with(nextWorkDay);
        assertEquals(LocalDate.of(2016, 5, 9), nextWordDay);
    }
    
    //可以避免上个例子中的强转操作
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
        
        LocalDate day = LocalDate.of(2016, 5, 7);//周六
        LocalDate nextWordDay = day.with(nextWorkDay);
        assertEquals(LocalDate.of(2016, 5, 9), nextWordDay);
    }
    
    public void testLocalTime()
    {
        Instant instant = Instant.now();//用long表示从1970-1-1 00:00:00到现在的nanosecond。打印结果和LocalDateTime差不多
        System.out.println(instant);
        
        LocalTime time = LocalTime.now();//LocalTime为一天中的某个时间
        System.out.println(time);
        
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);//打印和Instant差不多。 表示本地时间，如果要带时区，可以用ZonedDateTime
        
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

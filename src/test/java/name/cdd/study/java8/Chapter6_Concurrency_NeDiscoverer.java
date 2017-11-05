package name.cdd.study.java8;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;

public class Chapter6_Concurrency_NeDiscoverer
{
    public List<Ne> findNEsBySnmp()
    {
        Stream<String> ips = buildTargetIpAddresses();

        List<CompletableFuture<Ne>> futures = ips
           .map(ip -> CompletableFuture.supplyAsync(() -> discoverNe(ip)))
           .map(f -> f.exceptionally(ex -> null))
           .peek(f -> f.whenComplete((ne, th) -> System.out.println(ne.ip + " discovered.")))
           .collect(Collectors.toList());
        
        return futures.stream().map(CompletableFuture::join).filter(Objects::nonNull).collect(Collectors.toList());
    }
    
    public List<Ne> findNEsBySnmp2()
    {
        Stream<String> ips = buildTargetIpAddresses();
        
        return ips.parallel().map(ip -> discoverNe(ip))
                      .filter(Objects::nonNull)
                      .peek(ne -> System.out.println(ne.ip + " discovered."))
                      .collect(Collectors.toList());
    }
    
    public List<Ne> findNEsBySnmp3()
    {
        ExecutorService executor = Executors.newFixedThreadPool(100, r -> {Thread t = new Thread(r); t.setDaemon(true); return t;});
        
        Stream<String> ips = buildTargetIpAddresses();

        List<CompletableFuture<Ne>> futures = ips
           .map(ip -> CompletableFuture.supplyAsync(() -> discoverNe2(ip), executor))
           .collect(Collectors.toList());
        
        return futures.stream().map(CompletableFuture::join).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private Stream<String> buildTargetIpAddresses()
    {
        return Lists.newArrayList("1.2.3.4", "1.2.3.5", "1.2.3.6", "1.2.3.7", "1.2.3.4", "1.2.3.5", "1.2.3.6", "1.2.3.7"
                                 , "1.2.3.4", "1.2.3.5", "1.2.3.6", "1.2.3.7", "1.2.3.4", "1.2.3.5", "1.2.3.6", "1.2.3.7"
                                 ,"1.2.3.4", "1.2.3.5", "1.2.3.6", "1.2.3.7", "1.2.3.4", "1.2.3.5", "1.2.3.6", "1.2.3.7", "1.2.3.25").stream();
    }

    private Ne discoverNe(String ip)
    {
        delay();
//        throw new RuntimeException("aaaaaaccc");//抛异常只有findNEsBySnmp能处理
        return new Ne("aaa", ip);
    }
    
    private Ne discoverNe2(String ip)
    {
        delay();
        
//        System.out.println(ip + " discovered.");
//        return new Ne("aaa", ip);
        
        return null;
    }

    private void delay()
    {
        long ms = (long) (1000 * Math.random()); 
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        Instant start = Instant.now();
        
        doDiscover();
       
        System.out.println("Time cost: " + timeCost(start) + ".");
    }

    private static String timeCost(Instant start)
    {
        return Duration.between(start, Instant.now()).toMillis() + " ms";
    }

    private static void doDiscover()
    {
        List<Ne> result = new Chapter6_Concurrency_NeDiscoverer().findNEsBySnmp3();
        
        System.out.println("\nAll done! Discovered NE number: " + result.size() + "\n");
        result.forEach(System.out::println);
    }
    
    private class Ne
    {
        public String sysoid;
        public String ip;
        
        public Ne(String sysoid, String ip)
        {
            this.sysoid = sysoid;
            this.ip = ip;
        }
        
        @Override
        public String toString()
        {
            return "ip == " + ip + ", sysoid == " + sysoid;
        }
    }
    
}

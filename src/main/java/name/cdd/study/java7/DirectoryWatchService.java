package name.cdd.study.java7;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class DirectoryWatchService
{
    @SafeVarargs
    public static void watchMulti(String dir, WatchEvent.Kind<Path>... types) throws IOException, InterruptedException
    {
        WatchService service = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(dir).toAbsolutePath();
        
        path.register(service, types);
        
        while(true)
        {
            WatchKey key = service.take();
            
            for(WatchEvent<?> event : key.pollEvents())
            {
                Path createdPath = (Path)event.context();
                System.out.println(event.count() + "|" + event.kind() + "|" + createdPath.toString());
            }
            
            key.reset();
        }
    }
    
    @SuppressWarnings ("static-access")
    public static void main(String[] args) throws IOException, InterruptedException
    {
        new DirectoryWatchService().watchMulti("F:\\cdd", StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_MODIFY);
    }
}

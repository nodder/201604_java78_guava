package name.cdd.study.guava;

import java.util.Arrays;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

import junit.framework.TestCase;
import name.cdd.study.guava.Book;

public class BasicUtilitiesTest extends TestCase
{
    public void testJoiner()
    {
        assertEquals("a|b|c", Joiner.on('|').skipNulls().join(new String[] {"a", "b", "c"}));
        assertEquals("a|b|c", Joiner.on('|').skipNulls().join(new String[] {"a", "b", null, "c"}));
        
        assertEquals("a|b|c",          Joiner.on('|').useForNull("no value").join(new String[] {"a", "b", "c"}));
        assertEquals("a|b|no value|c", Joiner.on('|').useForNull("no value").join(new String[] {"a", "b", null, "c"}));
    }
    
    public void testSplitter()
    {
        assertEquals(Arrays.toString(new String[] {"a", "b", "c"}), Splitter.on("@@").split("a@@b@@c").toString());
    }
    
    //The Strings class provides a few handy utility methods for working with strings.
    public void testStrings()
    {
        assertEquals("fooxxxx", Strings.padEnd("foo",7,'x'));
        assertEquals("00000101", Strings.padStart("101", 8, '0'));
        
        assertEquals("", Strings.nullToEmpty(null));
        assertEquals("", Strings.nullToEmpty(""));
        assertEquals("foo", Strings.nullToEmpty("foo"));
        
        assertEquals(null, Strings.emptyToNull(""));
        
        assertTrue(Strings.isNullOrEmpty(""));
    }
    
    //The CharMatcher class provides functionality for working with characters based on the presence or absence of a type of character or a range of characters. 
    public void testCharMatcher()
    {
        assertEquals("String with    spaces and   tabs. ", CharMatcher.BREAKING_WHITESPACE.replaceFrom("String with    spaces and   tabs. ", ' '));
        assertEquals("989234", CharMatcher.JAVA_DIGIT.retainFrom("foo989yxbar234"));
    }
    
    //The Preconditions class is a collection of static methods used to verify the state of our code.
    public void testPreconditions()
    {
        try
        {
            Preconditions.checkNotNull(null, "cannot be null");
        }
        catch(Exception e)
        {
            assertEquals("cannot be null", e.getMessage());
        }
    }
    
    public void testHashcode()
    {
       assertEquals(28629151,  new Book().hashCode());
       //Book还有一个compare方法的实现。
    }
    
}

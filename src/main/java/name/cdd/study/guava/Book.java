package name.cdd.study.guava;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class Book implements Comparable<Book>
{
    private String author;
    private String title;
    private String publisher;
    private String isbn;
    private double price;
    
    public String getAuthor()
    {
        return author;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getPublisher()
    {
        return publisher;
    }
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }
    public String getIsbn()
    {
        return isbn;
    }
    public void setIsbn(String isbn)
    {
        this.isbn = isbn;
    }
    public double getPrice()
    {
        return price;
    }
    public void setPrice(double price)
    {
        this.price = price;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hashCode(author, title, publisher, isbn, price);
    }
    
    @Override
    public int compareTo(Book o)
    {
        return ComparisonChain.start()
                        .compare(this.title, o.getTitle())
                        .compare(this.author, o.getAuthor())
                        .compare(this.publisher, o.getPublisher())
                        .compare(this.isbn, o.getIsbn())
                        .compare(this.price, o.getPrice()).result();
    }
    
    
}

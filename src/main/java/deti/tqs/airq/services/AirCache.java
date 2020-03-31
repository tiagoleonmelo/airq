package deti.tqs.airq.services;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class AirCache
{

    @Id
    @GeneratedValue
    private long id;

    // private String cityName;
    // private CacheObject cacheObj;

    private int maxSize;

    
    // Constructor, getters and setters
    
    public AirCache(int maxSize)
    {
        this.maxSize = maxSize > 0 ? maxSize : 1;
    }

    public int getMaxSize()
    {
        return this.maxSize;
    }

    public void setMaxSize(int maxSize)
    {
        this.maxSize = maxSize;
    }

}
package deti.tqs.airq.services;

import java.util.HashMap;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class AirCache
{

    // @Id
    // @GeneratedValue
    // private long id;

    private String cityName;
    private CacheObject cacheObj;

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
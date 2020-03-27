package deti.tqs.airq.services;

import java.util.HashMap;

public class AirCache
{

    private HashMap<String, CacheObject> cache = new HashMap<>();
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

    //


    public CacheObject getAirQuality(String city)
    {

        if(this.cache.containsKey(city))
        {
            return this.cache.get(city);
        }

        // IDEA: Implement NullObject Software Pattern here
        return null;

    }

    public void putAirQuality(CacheObject obj)
    {

        // If we have memory shortage, THEN use the maxSize here
        // otherwise, it's overengineering
        this.cache.put(obj.getAirQuality().getCity(), obj);

    }

}
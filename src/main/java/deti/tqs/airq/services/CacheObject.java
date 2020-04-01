package deti.tqs.airq.services;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CacheObject
{

    @Id
    private String city;
    private int hits;
    private int misses;
    private long ttl;
    private long lastAccess;

    public CacheObject(){
        
    }

    public CacheObject(String city, int hits, int misses, long ttl) {
        this.city = city;
        this.hits = hits;
        this.misses = misses;
        this.ttl = ttl;
        this.lastAccess = System.currentTimeMillis();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getMisses() {
        return misses;
    }

    public void setMisses(int misses) {
        this.misses = misses;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public long getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(long lastAccess) {
        this.lastAccess = lastAccess;
    }
    

}
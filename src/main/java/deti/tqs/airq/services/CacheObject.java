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

    @Override
    public String toString() {
        return "CacheObject [city=" + city + ", hits=" + hits + ", lastAccess=" + lastAccess + ", misses=" + misses
                + ", ttl=" + ttl + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + hits;
        result = prime * result + (int) (lastAccess ^ (lastAccess >>> 32));
        result = prime * result + misses;
        result = prime * result + (int) (ttl ^ (ttl >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CacheObject other = (CacheObject) obj;
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (hits != other.hits)
            return false;
        if (lastAccess != other.lastAccess)
            return false;
        if (misses != other.misses)
            return false;
        if (ttl != other.ttl)
            return false;
        return true;
    }
    

}
package deti.tqs.airq.services;


import deti.tqs.airq.entities.AirQuality;

public class CacheObject
{

    private AirQuality airQuality;
    private int hits;
    private int misses;
    private double ttl;

    public CacheObject(AirQuality airQuality, int hits, int misses, double ttl) {
        this.airQuality = airQuality;
        this.hits = hits;
        this.misses = misses;
        this.ttl = ttl;
    }

    public AirQuality getAirQuality() {
        return airQuality;
    }

    public void setAirQuality(AirQuality airQuality) {
        this.airQuality = airQuality;
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

    public double getTtl() {
        return ttl;
    }

    public void setTtl(double ttl) {
        this.ttl = ttl;
    }

    public String getCity()
    {
        return this.airQuality.getCity();
    }

    

}
package deti.tqs.airq.services;

import org.springframework.stereotype.Service;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.repo.AirRepository;

@Service
public class AirService
{

    private AirRepository airRepository;
    private AirCache airCache;


    // Constructor, getters and setters

    public AirService(AirRepository airRepository, AirCache airCache) {
        this.airRepository = airRepository;
        this.airCache = airCache;
    }

    public AirRepository getAirRepository() {
        return airRepository;
    }

    public void setAirRepository(AirRepository airRepository) {
        this.airRepository = airRepository;
    }

    //


    public AirQuality getAirForCity(String city)
    {
        CacheObject obj = this.airCache.getAirQuality(city);

        if(obj != null)
        {
            return obj.getAirQuality();
        }

        // Here we make our API call
    }


    
}
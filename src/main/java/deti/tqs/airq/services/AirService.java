package deti.tqs.airq.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.repo.AirRepository;

@Service
public class AirService {

    @Autowired
    private AirRepository airRepository;
    // private AirCache airCache;
    private String key = "733ed6b1-d26a-48fb-bfcf-fd7b4bf500cd";
    private CityResolver cr = new CityResolver();

    // Constructor, getters and setters

    public AirService(AirRepository airRepository) {
        this.airRepository = airRepository;
        // this.airCache = airCache;
    }

    public AirRepository getAirRepository() {
        return airRepository;
    }

    public void setAirRepository(AirRepository airRepository) {
        this.airRepository = airRepository;
    }

    //

    public AirQuality getAirForCity(String country, String state, String city) throws UnirestException {
        // CacheObject obj = this.airCache.getAirQuality(city);

        // if(obj != null)
        // {
        // return obj.getAirQuality();
        // }

        // 10.3;20.7
        String coords = this.cr.getCityCoordinates(country, state, city);
        System.out.println(coords);
        // Here we make our API call
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest
                .get("https://api.waqi.info/feed/geo:" + coords + "/?token=a0c169b71b3dc333f54465c830bf391825164f7e")
                .asString();

        return new AirQuality(city, "PM10", "CO2", "O3");
    }

}
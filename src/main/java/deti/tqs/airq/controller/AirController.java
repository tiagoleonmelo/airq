package deti.tqs.airq.controller;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.services.AirService;
import deti.tqs.airq.services.CacheObject;

@RestController
public class AirController {

    @Autowired
    private AirService airService;

    // Constructor, getters and setters

    public AirController(AirService airService) {
        this.airService = airService;
    }

    public AirService getAirService() {
        return airService;
    }

    public void setAirService(AirService airService) {
        this.airService = airService;
    }

    //

    @GetMapping("/air/{country}/{state}/{cityName}")
    private AirQuality getCityAirQuality(@PathVariable String country, @PathVariable String state,
            @PathVariable String cityName) throws UnirestException
    {

        // Cache the result
        AirQuality airq = this.airService.getAirForCity(country, state, cityName);
        // CacheObject cacheObj =
        return airq;

    }

    


}
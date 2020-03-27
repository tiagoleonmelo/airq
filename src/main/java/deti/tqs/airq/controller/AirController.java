package deti.tqs.airq.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.services.AirService;

@RestController
public class AirController
{

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


    @GetMapping("/air/{cityName}")
    private AirQuality getCityAirQuality(@PathVariable String cityName)
    {

        return this.airService.getCityAir(cityName);

    }

    


}
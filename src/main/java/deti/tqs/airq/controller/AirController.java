package deti.tqs.airq.controller;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.services.AirService;

@Controller
@RequestMapping("/")
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

    @RequestMapping("/{cityName}")
    private String getCityAirQuality(@PathVariable String cityName, Model model) throws UnirestException
    {

        // Cache the result
        AirQuality airq = this.airService.getAirForCity(cityName);
        // CacheObject cacheObj =
        model.addAttribute("air", airq);
        return "index";

    }

    


}
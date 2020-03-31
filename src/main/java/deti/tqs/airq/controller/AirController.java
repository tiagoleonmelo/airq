package deti.tqs.airq.controller;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("/")
    private String index(Model model)
    {
        model.addAttribute("air", new AirQuality("", ""));

        return "index";

    }

    @RequestMapping("/{cityName}")
    private String getCityAirQuality(@PathVariable String cityName, Model model) throws UnirestException
    {

        // Cache the result
        AirQuality airq = this.airService.getAirForCity(cityName);

        // CacheObject cacheObj =
        model.addAttribute("air", airq);

        return "index";

    }

    @RequestMapping("/api/{cityName}")
    @ResponseBody
    private AirQuality apiGetCityAirQuality(@PathVariable String cityName, Model model) throws UnirestException
    {

        // TODO: Cache the result
        AirQuality airq = this.airService.getAirForCity(cityName);
        return airq;

    }


    @PostMapping("/")
    private String search(@RequestParam String q, Model model) throws UnirestException
    {

        // Cache the result
        AirQuality airq = this.airService.getAirForCity(q);

        // CacheObject cacheObj =
        model.addAttribute("air", airq);

        return "redirect:/"+q+"#intro";

    }    

    


}
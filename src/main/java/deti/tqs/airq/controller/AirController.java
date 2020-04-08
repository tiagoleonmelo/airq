package deti.tqs.airq.controller;

import java.util.List;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.services.AirService;
import deti.tqs.airq.services.CacheObject;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/")
public class AirController {

    @Autowired
    private AirService airService;

    // Constructor

    public AirController(AirService airService) {
        this.airService = airService;
    }

    //

    
    // Endpoint Mapping

    @GetMapping(value="/")
    public String index(Model model)
    {

        model.addAttribute("air", new AirQuality("", ""));

        return "index";

    }

    @GetMapping(value="/search/{cityName}")
    public String searchCityAirQuality(@PathVariable String cityName, Model model) throws UnirestException
    {

        AirQuality airq = this.airService.getAirForCity(formatQuery(cityName));
        
        model.addAttribute("air", airq);

        return "index";

    }


    @GetMapping(value="/api/metadata")
    @ResponseBody
    @ApiOperation(value = "Greets a person given her name")
    public List<CacheObject> getCacheMetadata(Model mode)
    {
        return this.airService.getCacheMetadata();
    }


    @GetMapping(value="/api/{cityName}")
    @ResponseBody
    public AirQuality apiGetCityAirQuality(@PathVariable String cityName, Model model) throws UnirestException
    {

        return this.airService.getAirForCity(formatQuery(cityName));

    }


    @PostMapping("/")
    public String search(@RequestParam String q, Model model)
    {

        return "redirect:/search/" + formatQuery(q) + "#results";

    }    

    //


    // Helper functions

    private String formatQuery(String city){

        String[] cityNames = city.split(" ");
        StringBuilder sb = new StringBuilder();

        for (String string : cityNames) {
            
            sb.append(string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase());
            sb.append(" ");

        }

        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    


}
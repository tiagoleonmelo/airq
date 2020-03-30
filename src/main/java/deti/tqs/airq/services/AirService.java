package deti.tqs.airq.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONObject;
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

    public AirQuality getAirForCity(String city) throws UnirestException {
        // CacheObject obj = this.airCache.getAirQuality(city);

        // if(obj != null)
        // {
        // return obj.getAirQuality();
        // }

        // Here we make our API call
        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest
                .get("https://api.ambeedata.com/latest/by-city?city=" + city)
                .header("accept", "application/json")
                .header("x-api-key", "xmDoN21nog79FuIzd5968aV3ygsNteMN7X1ivXKc")
                .asJson();

        JSONObject jsonObject = response.getBody().getObject();
        JSONArray stations = jsonObject.getJSONArray("stations");
        JSONObject first;

        if(stations.length() > 0)
        {
            first = stations.getJSONObject(0);
        }
        else
        {
            System.out.println("ERROR: Not found");
            return null;
        }


        return new AirQuality(city, first.getString("PM10"), first.getString("CO"), first.getString("OZONE"), first.getString("AQI"));
    }

}
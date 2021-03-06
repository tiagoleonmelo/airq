package deti.tqs.airq.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.apache.log4j.Logger;
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

    @Autowired
    private CacheManager cacheManager;

    private String key = "xmDoN21nog79FuIzd5968aV3ygsNteMN7X1ivXKc";
    private String breezometerKey = "dbc7fe0865814b06b30505a924f32f4f";
    static final Logger logger = Logger.getLogger(AirService.class);

    private static final String OZONE = "OZONE";

    // Constructor

    public AirService(AirRepository airRepository) {
        this.airRepository = airRepository;
        this.cacheManager = new CacheManager(airRepository);
    }

    public AirService() {

    }

    //

    public AirQuality getAirForCity(String city) throws UnirestException {

        AirQuality returnable;

        // Check if we have this request cached
        if (this.cacheManager.containsCached(city)) {

            returnable = this.cacheManager.getCached(city);

        } else {

            // If not, we make our API call, instance a CacheObject and add 1 miss to this
            returnable = this.apiCall(city);
            this.cacheManager.storeInCache(returnable);

        }

        return returnable;
    }

    public List<CacheObject> getCacheMetadata() {
        return this.airRepository.findAll();
    }

    public AirQuality apiCall(String city) throws UnirestException {

        JSONObject firstStation = this.getFirstAmbeeStation(city);

        if(firstStation == null){
            return null;
        }

        return new AirQuality(firstStation.getString("countryCode"), city)
                .putAttr("PM10", Double.toString(firstStation.getDouble("PM10")))
                .putAttr("CO", Double.toString(firstStation.getDouble("CO")))
                .putAttr(OZONE, Double.toString(firstStation.getDouble(OZONE)))
                .putAttr("AQI", Double.toString(firstStation.getDouble("AQI")));

    }

    public String getLatLng(String city) throws UnirestException {

        JSONObject first = this.getFirstAmbeeStation(city);

        if(first == null){
            return null;
        }
        
        return first.getDouble("lat") + "/" + first.getDouble("lng") + "/" + first.getString("countryCode");

    }

    private JSONObject getFirstAmbeeStation(String city) throws UnirestException {

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest
                .get("https://api.ambeedata.com/latest/by-city?city=" + String.join("%20", city.split(" ")))
                .header("accept", "application/json").header("x-api-key", this.key).asJson();

        JSONObject jsonObject = response.getBody().getObject();
        JSONArray stations = jsonObject.getJSONArray("stations");
        JSONObject first;

        if (stations.length() > 0) {
            first = stations.getJSONObject(0);
        } else {
            logger.error("ERROR: City " + city + " not found!");

            return null;
        }

        return first;

    }

    public Map<String, AirQuality> getAirHistoryForCity(String city, int hours) throws UnirestException {
        // Since historical data is not available on Ambee, we will be using Breezometer
        // But we still need Ambee to get the Lat and Long given a city name

        String[] coords = this.getLatLng(city).split("/");
        String country = coords[2];
        HashMap<String, AirQuality> ret = new HashMap<>();

        HttpResponse<JsonNode> response = Unirest
                .get("https://api.breezometer.com/air-quality/v2/historical/hourly?lat=" + coords[0] + "&lon="
                        + coords[1] + "&key=" + this.breezometerKey + "&hours=" + Integer.toString(hours)
                        + "&features=pollutants_concentrations,breezometer_aqi")
                .asJson();

        JSONObject jsonObject = response.getBody().getObject();

        JSONArray data = jsonObject.getJSONArray("data");

        JSONObject temp;
        AirQuality airQuality;
        String aqi;
        String o3;
        String co;
        String pm10;

        String poll = "pollutants";
        String con = "concentration";
        String val = "value";

        for (int i = 0; i < hours; i++) {
            temp = data.getJSONObject(i);

            if(!temp.getBoolean("data_available"))
            {
                logger.error("ERROR Data Unavailable");
                continue;
            }

            aqi = temp.getJSONObject("indexes").getJSONObject("baqi").getString("aqi_display");
            o3 = Double.toString(temp.getJSONObject(poll).getJSONObject("o3").getJSONObject(con)
                    .getDouble(val));
            co = Double.toString(temp.getJSONObject(poll).getJSONObject("co").getJSONObject(con)
                    .getDouble(val));
            pm10 = Double.toString(temp.getJSONObject(poll).getJSONObject("pm10")
                    .getJSONObject(con).getDouble(val));

            airQuality = new AirQuality(country, city)
                    .putAttr("PM10", pm10)
                    .putAttr("CO", co)
                    .putAttr(OZONE, o3)
                    .putAttr("AQI", aqi);

            ret.put(temp.getString("datetime"), airQuality);
        }

        return ret;
    }
}
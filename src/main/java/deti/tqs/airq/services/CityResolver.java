package deti.tqs.airq.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONObject;


public class CityResolver {

    private String key = "733ed6b1-d26a-48fb-bfcf-fd7b4bf500cd";

    public String getCityCoordinates(String country, String state, String city)
            throws UnirestException {

        ObjectMapper objectMapper = new ObjectMapper();

        Unirest.setTimeouts(0, 0);
        HttpResponse<JsonNode> response = Unirest
                .get("http://api.airvisual.com/v2/city?city=" + city + "&state=" + state + "&country=" + country + "&key=" + this.key)
                .asJson();

        JSONObject myObj = response.getBody().getObject();
        String msg = myObj.getString("data");

        JsonObject jsonObject = JsonParser.parseString(msg).getAsJsonObject();

        String coords = jsonObject.getAsJsonObject("location").get("coordinates").toString();

        coords = coords.replace("[", "");
        coords = coords.replace("]", "");

        String str_coords = coords.split(",")[1] + ";" + coords.split(",")[0];

        System.out.println(str_coords);
        return str_coords;
    }

}
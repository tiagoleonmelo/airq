package deti.tqs.airq;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.jupiter.api.Test;

import deti.tqs.airq.services.CityResolver;

public class CityResolverTest {

    CityResolver sutCityResolver = new CityResolver();

    @Test
    public void whenGetCoordsForCity_thenReturnCoords() throws UnirestException
    {

        String coords = this.sutCityResolver.getCityCoordinates("portugal", "coimbra", "coimbra");
        
        assertEquals("40.206944444444446;-8.410833333333334", coords);

    }
}
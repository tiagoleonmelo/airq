package deti.tqs.airq.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.repo.AirRepository;
import deti.tqs.airq.services.AirService;

@ExtendWith(MockitoExtension.class)
public class AirServiceTest {

    @Mock
    AirRepository airRepository;

    @InjectMocks
    AirService sutAirServiceSut;

    @Test
    public void whenGetCoimbraStats_returnCoimbraStats() throws UnirestException
    {

        AirQuality cbr = sutAirServiceSut.getAirForCity("Coimbra");

        assertEquals(new AirQuality("PT", "Coimbra")
                            .putAttr("PM10", "18.97")
                            .putAttr("CO", "0.63")
                            .putAttr("OZONE", "69")
                            .putAttr("AQI", "91")
                            .getCity(), cbr.getCity());


    }

    @Test
    public void whenGetHistoricalDataForXHours_getXHours() throws UnirestException
    {

        int hours = 2;

        Map<String, AirQuality> hm = sutAirServiceSut.getAirHistoryForCity("Coimbra", hours);

        assertTrue(hours >= hm.size());

    }


    @Test
    public void whenGetHistoricalDataForCity_getOnlyCity() throws UnirestException
    {

        Map<String, AirQuality> hm = sutAirServiceSut.getAirHistoryForCity("Coimbra", 3);

        for (String key : hm.keySet()) {

            assertEquals("Coimbra", hm.get(key).getCity());
            
        }
    }

    @Test
    public void whenGetCoordinatesForCoimbra_getRightCoordinates() throws UnirestException
    {
        assertEquals("40.2056/-8.4196/PT", sutAirServiceSut.getLatLng("Coimbra"));
    }


    @BeforeEach
    public void setUp() {

    }

}
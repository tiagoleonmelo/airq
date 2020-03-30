package deti.tqs.airq;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.repo.AirRepository;
import deti.tqs.airq.services.AirService;

public class AirServiceTest {

    @Mock
    AirRepository airRepository;

    @InjectMocks
    AirService sutAirServiceSut = new AirService(airRepository);

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void whenGetCoimbraStats_returnCoimbraStats() throws UnirestException
    {

        AirQuality cbr = sutAirServiceSut.getAirForCity("coimbra");

        assertEquals(new AirQuality("coimbra", "18.97", "0.63", "69", "91"), cbr);

    }


}
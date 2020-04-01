package deti.tqs.airq;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @BeforeEach
    public void setUp() {

    }

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

    // TODO: Test if its using repo and cache when it should (2 tests)


}
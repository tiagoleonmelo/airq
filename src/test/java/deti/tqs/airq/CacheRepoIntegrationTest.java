package deti.tqs.airq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.repo.AirRepository;
import deti.tqs.airq.services.CacheManager;

@AutoConfigureTestDatabase
public class CacheRepoIntegrationTest {



    @Autowired
    AirRepository airRepository;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    public void setUpAir() throws Exception
    {
        
    }


    @Test
    public void whenHit_hitIsRecorded()
    {
        
        AirQuality airQuality = new AirQuality("PT", "Coimbra")
        .putAttr("PM10", "18.97")
        .putAttr("CO", "0.63")
        .putAttr("OZONE", "69")
        .putAttr("AQI", "91");

        // airRepository.save(airQuality);

        cacheManager.storeInCache(airQuality);

        assertTrue(cacheManager.containsCached("Coimbra"));

        cacheManager.getCached("Coimbra");

        assertEquals(1, airRepository.findById("Coimbra").get().getHits());

    }
    
    @Test
    public void whenMiss_missIsRecorded()
    {

    }

}
package deti.tqs.airq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.repo.AirRepository;
import deti.tqs.airq.services.AirService;
import deti.tqs.airq.services.CacheManager;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class ServiceCacheRepoIntegrationTest {

    @Autowired
    AirService airService;

    @Autowired
    AirRepository airRepository;

    @Autowired
    CacheManager cacheManager;

    @BeforeEach
    public void setUpAir() throws Exception {

    }

    @Test
    public void whenHit_hitIsRecorded() {

        AirQuality airQuality = new AirQuality("PT", "Coimbra").putAttr("PM10", "18.97").putAttr("CO", "0.63")
                .putAttr("OZONE", "69").putAttr("AQI", "91");

        // airQuality should be being stored in repo
        cacheManager.storeInCache(airQuality);

        assertTrue(cacheManager.containsCached("Coimbra"));

        // We should be fetching "Coimbra" from cache, since ttl hasn't expired yet
        // Therefore, we should record a hit
        cacheManager.getCached("Coimbra");

        assertEquals(1, airRepository.findById("Coimbra").get().getHits());

    }

    @Test
    public void whenMiss_missIsRecorded() throws InterruptedException, UnirestException
    {

        AirQuality airQuality = new AirQuality("PT", "Coimbra")
        .putAttr("PM10", "18.97")
        .putAttr("CO", "0.63")
        .putAttr("OZONE", "69")
        .putAttr("AQI", "91");

        // airQuality should be being stored in repo and cache
        cacheManager.storeInCache(airQuality);

        assertTrue(cacheManager.containsCached("Coimbra"));

        // Removing airQuality from cache
        cacheManager.fullClear();


        // Now when we try to access "Coimbra", we should be recording a miss
        // Since it surely isn't cached
        airService.getAirForCity("Coimbra");

        
        // This equals a total of 2 misses, since a CacheObject is initialized with
        // a default value of miss = 1
        assertEquals(2, airRepository.findById("Coimbra").get().getMisses());

    }

}
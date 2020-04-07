package deti.tqs.airq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.repo.AirRepository;
import deti.tqs.airq.services.CacheManager;

@ExtendWith(MockitoExtension.class)
public class CacheManagerTest {

    @Mock
    AirRepository airRepository;

    @InjectMocks
    CacheManager cacheManagerSut;

    @BeforeEach
    public void setUp() {

    }

    @AfterEach
    public void resetDb() {
        airRepository.deleteAll();
    }


    @Test
    public void whenAddToCache_CacheSizeIsOne()
    {

        AirQuality airQuality = new AirQuality("PT", "Coimbra")
        .putAttr("PM10", "18.97")
        .putAttr("CO", "0.63")
        .putAttr("OZONE", "69")
        .putAttr("AQI", "91");

        cacheManagerSut.storeInCache(airQuality);

        assertEquals(1, cacheManagerSut.getSize());

    }

    @Test
    public void whenAddToCache_CacheContainsAdded()
    {

        AirQuality airQuality = new AirQuality("PT", "Coimbra")
        .putAttr("PM10", "18.97")
        .putAttr("CO", "0.63")
        .putAttr("OZONE", "69")
        .putAttr("AQI", "91");

        cacheManagerSut.storeInCache(airQuality);

        assertTrue(cacheManagerSut.containsCached("Coimbra"));

    }

    @Test
    public void whenAddToCache_AddedElementIsCorrect()
    {

        AirQuality airQuality = new AirQuality("PT", "Coimbra")
        .putAttr("PM10", "18.97")
        .putAttr("CO", "0.63")
        .putAttr("OZONE", "69")
        .putAttr("AQI", "91");

        cacheManagerSut.storeInCache(airQuality);

        assertTrue(cacheManagerSut.containsCached("Coimbra"));

        assertEquals(airQuality, cacheManagerSut.getCached("Coimbra"));

    }

    @Test
    public void whenTimePasses_CacheExpires() throws InterruptedException
    {

        AirQuality airQuality = new AirQuality("PT", "Coimbra")
        .putAttr("PM10", "18.97")
        .putAttr("CO", "0.63")
        .putAttr("OZONE", "69")
        .putAttr("AQI", "91");

        cacheManagerSut.storeInCache(airQuality);

        assertTrue(cacheManagerSut.containsCached("Coimbra"));

        cacheManagerSut.fullClear();

        assertFalse(cacheManagerSut.containsCached("Coimbra"));
        assertEquals(0, cacheManagerSut.getSize());

    }

    @Test
    public void whenSameKeyInsert_UpdateOccurs() throws InterruptedException
    {

        AirQuality airQuality = new AirQuality("PT", "Coimbra")
        .putAttr("PM10", "18.97")
        .putAttr("CO", "0.63")
        .putAttr("OZONE", "69")
        .putAttr("AQI", "91");

        AirQuality braveNewAirQuality = new AirQuality("PT", "Coimbra")
        .putAttr("PM10", "18.97")
        .putAttr("CO", "0.63")
        .putAttr("OZONE", "70")
        .putAttr("AQI", "91");

        cacheManagerSut.storeInCache(airQuality);

        assertTrue(cacheManagerSut.containsCached("Coimbra"));

        cacheManagerSut.storeInCache(braveNewAirQuality);

        assertTrue(cacheManagerSut.containsCached("Coimbra"));
        assertEquals(1, cacheManagerSut.getSize());
        assertEquals("70", cacheManagerSut.getCached("Coimbra").getAttributes().get("OZONE"));

    }

}
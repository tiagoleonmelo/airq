package deti.tqs.airq;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import deti.tqs.airq.repo.AirRepository;
import deti.tqs.airq.services.CacheObject;

@DataJpaTest
public class AirRepositoryTest {

    @Autowired
    private AirRepository airRepositorySut;

    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void getCachedAir_returnsAirMetadata() 
    {

        CacheObject savedCache = entityManager.persistAndFlush(new CacheObject("Viseu", 0, 0, 3));

        CacheObject cache = airRepositorySut.findById("Viseu").get();

        assertEquals(savedCache, cache);

    }

    @Test
    public void saveCache_cacheIsSaved()
    {

        CacheObject obj = new CacheObject("Viseu", 0, 0, 3);

        airRepositorySut.save(obj);

        CacheObject cache = airRepositorySut.findById("Viseu").get();

        assertEquals(obj, cache);

    }



}
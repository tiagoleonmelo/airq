package deti.tqs.airq.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.repo.AirRepository;

@Component
public class CacheManager {

    @Autowired
    private AirRepository airRepository;

    private HashMap<String, AirQuality> cache = new HashMap<>();
    private HashMap<String, CacheObject> cacheData = new HashMap<>();


    // Constructor, getters and setters

    public CacheManager(AirRepository airRepository) {
        this.airRepository = airRepository;
    }

    public AirRepository getAirRepository() {
        return airRepository;
    }

    public void setAirRepository(AirRepository airRepository) {
        this.airRepository = airRepository;
    }

    //

    public int getSize() {
        synchronized (cache) {
            return this.cache.size();
        }
    }

    public boolean containsCached(String city) {
        synchronized (cache) {
            return this.cache.containsKey(city);
        }
    }

    public AirQuality getCached(String city) {
        synchronized (cache) {

            // Updating CacheObject metadata
            CacheObject hit = this.cacheData.get(city);
            hit.setLastAccess(System.currentTimeMillis());
            hit.setHits(hit.getHits() + 1);
            this.airRepository.save(hit);

            return this.cache.get(city);
        }
    }

    public void remove(String city) {
        synchronized (cache) {
            cache.remove(city);
            cacheData.remove(city);
        }
    }

    public void storeInCache(AirQuality airQuality) {
        
        // Get the attributes in our Repo with Key equal to airQuality.getCity()
        CacheObject temp;
        
        if(this.airRepository.existsById(airQuality.getCity()))
        {
            Optional<CacheObject> retrieved = this.airRepository.findById(airQuality.getCity());
            temp = retrieved.get();
        }
        else
        {
            // Each CacheObject is initialized with a default value of ttl = 3
            temp = new CacheObject(airQuality.getCity(), 0, 0, 3);
            this.airRepository.save(temp);
        }

        synchronized (cache) {
            cache.put(airQuality.getCity(), airQuality);
            cacheData.put(airQuality.getCity(), temp);
        }

        final long tempTtl = temp.getTtl();

        // Program a thread to delete this object after temp.ttl
        Thread t = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(tempTtl * 1000);
                    } catch (InterruptedException ex) {
                    }
                    cleanup();
                }
            }
        });

        t.setDaemon(true);
        t.start();

    }
    
    public void cleanup() {
 
        long now = System.currentTimeMillis();
        ArrayList<String> deleteKey = null;
 
        synchronized (cache) {
 
            deleteKey = new ArrayList<String>((cache.size() / 2) + 1);
            String key = null;
            AirQuality c = null;

            for (Map.Entry<String, AirQuality> entry : cache.entrySet()) {
                key = (String) entry.getKey();
                c = (AirQuality) entry.getValue();
 
                if (c != null && (now > (this.cacheData.get(key).getTtl() + this.cacheData.get(key).getLastAccess()))) {
                    deleteKey.add(key);
                }

            }
 
        }
 
        for (String key : deleteKey) {
            synchronized (cache) {
                cache.remove(key);
            }
 
            Thread.yield();
        }
    }

    //



    


}
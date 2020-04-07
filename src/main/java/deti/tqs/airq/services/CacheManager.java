package deti.tqs.airq.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
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

    static final Logger logger = Logger.getLogger(CacheManager.class);


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

    public void fullClear() {
        synchronized (cache) {
            this.cache.clear();
            this.cacheData.clear();
        }
    }

    public int getSize() {
        synchronized (cache) {
            return this.cache.size();
        }
    }

    public boolean containsCached(String city) {
        synchronized (cache) {
            logger.info( Integer.toString(cache.size()) );
            return this.cache.containsKey(city);
        }
    }

    public AirQuality getCached(String city) {
        synchronized (cache) {

            if(this.containsCached(city)) {
                // Updating CacheObject metadata
                CacheObject hit = this.cacheData.get(city);
                hit.setLastAccess(System.currentTimeMillis());
                hit.setHits(hit.getHits() + 1);
                this.airRepository.save(hit);

                logger.info("Accessing cache!");

                return this.cache.get(city);
            } else {
                return null;
            }
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
            if(this.airRepository.findById(airQuality.getCity()).isPresent()){ 
                temp = this.airRepository.findById(airQuality.getCity()).get();
                temp.setMisses(temp.getMisses() + 1);
                temp.setRequests(temp.getRequests() + 1);
                this.airRepository.save(temp);

                logger.debug("Adding 1 miss");
            } else {
                temp = new CacheObject();
            }
        }
        else
        {
            // Each CacheObject is initialized with a default value of ttl = 15
            temp = new CacheObject(airQuality.getCity(), 0, 1, 30);
            this.airRepository.save(temp);
            logger.debug("Creating a new CacheObject");

        }

        synchronized (cache) {
            cache.put(airQuality.getCity(), airQuality);
            cacheData.put(airQuality.getCity(), temp);
            logger.debug("Saving cache");
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
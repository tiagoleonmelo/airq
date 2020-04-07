package deti.tqs.airq.functional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mashape.unirest.http.exceptions.UnirestException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import deti.tqs.airq.services.AirService;

public class AirqWebPageTest {

    AirQ airq;
    AirService airService = new AirService();

    @BeforeEach
    public void setUp() throws InterruptedException {
        airq = new AirQ();
    }

    @AfterEach
    public void tearDown() {
        airq.quit();
    }

    @Test
    public void whenPageStarts_noResults() {

        assertFalse(airq.existResults());

    }

    @Test
    public void whenSearchForCoimbra_coimbraAppears() {

        assertFalse(airq.existResults());

        airq.search("Coimbra");

        assertTrue(airq.existResults());

        assertTrue(airq.foundDataFor("Coimbra"));

    }

    @Test
    public void whenSearchForCoimbra_correctCoimbraDataIsDisplayed() throws UnirestException {

        assertFalse(airq.existResults());

        airq.search("Coimbra");

        assertTrue(airq.existResults());

        assertTrue(airq.foundDataFor("Coimbra"));

        airq.dataDisplayed_isCorrect(airService.apiCall("Coimbra"));

    }

}
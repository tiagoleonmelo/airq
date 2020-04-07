package deti.tqs.airq;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.services.AirService;

@WebMvcTest
public class AirControllerTest {

    @Autowired
    MockMvc servlet;

    @MockBean
    AirService airService;

    @Test
    public void whenGetAirForCity_thenReturnAirQuality() throws Exception {

        // "Programming" the mock
        given(airService.getAirForCity("/api/coimbra")).willReturn(new AirQuality("PT", "Coimbra")
                .putAttr("PM10", "18.97").putAttr("CO", "0.63").putAttr("OZONE", "69").putAttr("AQI", "91"));

        // Testing a GET on a given API endpoint

        // MvcResult result =
        // servlet.perform(MockMvcRequestBuilders.get("/api/coimbra"))
        // .andExpect(request().asyncStarted()).andReturn();

        // servlet.perform(MockMvcRequestBuilders.asyncDispatch(result)).andExpect(status().isAccepted())
        // .andExpect(jsonPath("city").value("Coimbra"));


        servlet.perform(MockMvcRequestBuilders.get("/api/coimbra")).andExpect(status().isOk())
                .andExpect(jsonPath("city").value("Coimbra"));


        // .andExpect(content().equals(new AirQuality("coimbra", "18.97", "0.63", "69",
        // "91")));

    }

}
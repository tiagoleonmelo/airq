package deti.tqs.airq;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import deti.tqs.airq.entities.AirQuality;
import deti.tqs.airq.services.AirService;

@WebMvcTest
public class AirControllerTest
{

    @Autowired
    MockMvc servlet;

    @MockBean
    AirService airService;


    @Test
    public void whenGetAirForCity_thenReturnAirQuality() throws Exception
    {
    
        // "Programming" the mock
        given(airService.getAirForCity("coimbra"))
        .willReturn(new AirQuality("coimbra", "PM10", "CO2", "O3"));

        // Testing a GET on a given end-point
        servlet.perform(MockMvcRequestBuilders.get("/air/coimbra"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("city").value("coimbra"));
        //.andExpect(content().equals(new AirQuality("coimbra", "PM10", "CO2", "O3")));


    }

}
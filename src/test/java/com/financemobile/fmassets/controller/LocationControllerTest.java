package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.CreateLocationDto;
import com.financemobile.fmassets.model.Location;
import com.financemobile.fmassets.security.OAuth2Helper;
import com.financemobile.fmassets.service.LocationService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LocationControllerTest extends OAuth2Helper {

    @MockBean
    private LocationService locationService;

    private final Gson gson = new Gson();

    @Test
    public void test_addLocation() throws Exception {

        // mock repo and response
        Location location = new Location();
        location.setId(2L);
        location.setName("Com 8");
        location.setCity("Tema");
        location.setCountry("Ghana");
        location.setCreatedBy("divine");
        location.setDateCreated(new Date());
        location.setDateModified(new Date());

        Mockito.when(locationService.addLocation(location.getName(),
                location.getCity(), location.getCountry()))
                .thenReturn(location);

        // payload for the endpoint
        CreateLocationDto createLocationDto = new CreateLocationDto();
        createLocationDto.setName(location.getName());
        createLocationDto.setCity(location.getCity());
        createLocationDto.setCountry(location.getCountry());

        // fire request
        mockMvc.perform(post("/location")
                .content(gson.toJson(createLocationDto))
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("data.id", is(location.getId().intValue())))
                .andExpect(jsonPath("data.name", is(location.getName())))
                .andExpect(jsonPath("data.city", is(location.getCity())))
                .andExpect(jsonPath("data.country", is(location.getCountry())));
    }

    @Test
    public void test_findAll() throws Exception {

        // mock repo and response
        Location location = new Location();
        location.setId(2L);
        location.setName("com 8");
        location.setCity("Tema");
        location.setCountry("Ghana");
        location.setCreatedBy("divine");
        location.setDateCreated(new Date());
        location.setDateModified(new Date());

        List<Location> locationList = Arrays.asList(location);

        Mockito.when(locationService.getAllLocations())
                .thenReturn(locationList);

        // fire request
        mockMvc.perform(get("/location")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("status", is(true)))
                .andExpect(jsonPath("message", is("Success")))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].id", is(location.getId().intValue())))
                .andExpect(jsonPath("$.data[0].name", is(location.getName())))
                .andExpect(jsonPath("$.data[0].city", is(location.getCity())))
                .andExpect(jsonPath("$.data[0].country", is(location.getCountry())));
    }
}



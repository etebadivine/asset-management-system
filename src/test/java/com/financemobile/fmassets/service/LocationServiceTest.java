package com.financemobile.fmassets.service;

import com.financemobile.fmassets.controller.AssignmentHistoryControllerTest;
import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Location;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.repository.LocationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void test_addLocation() {

        String name = "Comm 8";
        String city = "Tema";
        String country = "Ghana";
        CreateLocationDto createLocationDto = new CreateLocationDto();
        createLocationDto.setName(name);
        createLocationDto.setCity(city);
        createLocationDto.setCountry(country);
        Location location = locationService.addLocation(createLocationDto);

        Assertions.assertNotNull(location.getId());
        Assertions.assertEquals(location.getName(), name);
        Assertions.assertEquals(location.getCity(), city);
        Assertions.assertEquals(location.getCountry(), country);
        Assertions.assertNotNull(location.getCreatedBy());
        Assertions.assertNotNull(location.getDateCreated());
    }

    @Test
    public void test_addLocation_duplicate() {
        String name = "Comm 8";
        String city = "Tema";
        String country = "Ghana";
        CreateLocationDto createLocationDto = new CreateLocationDto();
        createLocationDto.setName(name);
        createLocationDto.setCity(city);
        createLocationDto.setCountry(country);
        locationService.addLocation(createLocationDto);

        Assertions.assertThrows(AlreadyExistException.class, () -> {
            locationService.addLocation(createLocationDto);
        });
    }

    @Test
    public void test_getLocationByName() {
        String name = "comm 8";
        String city = "Tema";
        String country = "Ghana";
        CreateLocationDto createLocationDto = new CreateLocationDto();
        createLocationDto.setName(name);
        createLocationDto.setCity(city);
        createLocationDto.setCountry(country);
        Location location = locationService.addLocation(createLocationDto);

        Location locat = locationService.getLocationByName(name);
        Assertions.assertEquals(location.getId(), locat.getId());
        Assertions.assertEquals(location.getName(),locat.getName());
        Assertions.assertEquals(location.getCity(), locat.getCity());
        Assertions.assertEquals(location.getCountry(), locat.getCountry());

    }


    @Test
    public void test_getLocationByName_notfound() {
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            locationService.getLocationByName("Greater Accra");
        });
    }

    @Test
    public void test_getLocationById(){
        String name = "comm 8";
        String city = "Tema";
        String country = "Ghana";
        CreateLocationDto createLocationDto = new CreateLocationDto();
        createLocationDto.setName(name);
        createLocationDto.setCity(city);
        createLocationDto.setCountry(country);
        Location location = locationService.addLocation(createLocationDto);

        Location locat = locationService.getLocationById(location.getId());
        Assertions.assertEquals(location.getId(), locat.getId());
        Assertions.assertEquals(location.getName(),locat.getName());
        Assertions.assertEquals(location.getCity(), locat.getCity());
        Assertions.assertEquals(location.getCountry(), locat.getCountry());
    }

    @Test
    public void test_getLocationById_notfound(){
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            locationService.getLocationById(3L);
        });
    }


    @Test
    public void test_getAllLocations(){
        String name = "comm 8";
        String city = "Tema";
        String country = "Ghana";
        CreateLocationDto createLocationDto = new CreateLocationDto();
        createLocationDto.setName(name);
        createLocationDto.setCity(city);
        createLocationDto.setCountry(country);
        Location location = locationService.addLocation(createLocationDto);

        List<Location> locationList = locationService.getAllLocations();

        Assertions.assertEquals(locationList.size(), 1);
        Assertions.assertEquals(locationList.get(0).getName(), location.getName());
        Assertions.assertEquals(locationList.get(0).getCity(), location.getCity());
        Assertions.assertEquals(locationList.get(0).getCountry(), location.getCountry());
    }

    @Test
    public void test_editLocation() {
        String name = "Comm 8";
        String city = "Tema";
        String country = "Ghana";
        CreateLocationDto createLocationDto = new CreateLocationDto();
        createLocationDto.setName(name);
        createLocationDto.setCity(city);
        createLocationDto.setCountry(country);
        Location location = locationService.addLocation(createLocationDto);

        EditLocationDto editLocationDto = new EditLocationDto();
        editLocationDto.setLocationId(location.getId());
        editLocationDto.setName("Community 8");
        editLocationDto.setCity("Tema");
        editLocationDto.setCountry("Ghana");

        Location location1 = locationService.editLocation(editLocationDto);

        Assertions.assertNotNull(location1.getId());
        Assertions.assertEquals(location1.getName(),editLocationDto.getName());
        Assertions.assertEquals(location1.getCity(),editLocationDto.getCity());
        Assertions.assertEquals(location1.getCountry(),editLocationDto.getCountry());
    }

    @Test
    public void test_editLocation_notFound(){
        EditLocationDto editLocationDto = new EditLocationDto();
        editLocationDto.setLocationId(40L);
        Assertions.assertThrows(DataNotFoundException.class, ()->{
            locationService.editLocation(editLocationDto);
        });
    }

    @Test
    public void test_removeLocation() {
        String name = "Amasaman";
        String city = "Accra";
        String country = "Ghana";
        CreateLocationDto createLocationDto = new CreateLocationDto();
        createLocationDto.setName(name);
        createLocationDto.setCity(city);
        createLocationDto.setCountry(country);
        Location location = locationService.addLocation(createLocationDto);
        locationService.removeLocation(location.getId());

        Assertions.assertThrows(DataNotFoundException.class, ()->{
            locationService.getLocationByName(name);
        });
    }

    @AfterEach
    public void tearDown() { locationRepository.deleteAll(); }
}
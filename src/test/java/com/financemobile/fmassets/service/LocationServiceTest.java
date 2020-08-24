package com.financemobile.fmassets.service;

import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Location;
import com.financemobile.fmassets.repository.LocationRepository;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    public void setup() { locationRepository.deleteAll(); }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void test_addLocation() {

        String name = "Comm 8";
        String city = "Tema";
        String country = "Ghana";
        Location location = locationService.addLocation(name, city, country);

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
        locationService.addLocation(name, city, country);

        Assertions.assertThrows(AlreadyExistException.class, () -> {
            locationService.addLocation(name, "Tema", "Ghana");
        });
    }

    @Test
    public void test_getLocationByName() {
        String name = "comm 8";
        String city = "Tema";
        String country = "Ghana";
        Location location = locationService.addLocation(name, city, country);

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
        Location location = locationService.addLocation(name, city, country);

        Location locat = locationService.getLocationById(location.getId());
        Assertions.assertEquals(location.getId(), locat.getId());
        Assertions.assertEquals(location.getName(),locat.getName());
        Assertions.assertEquals(location.getCity(), locat.getCity());
        Assertions.assertEquals(location.getCountry(), locat.getCountry());


    }

    @Test
    public void test_getLocationById_notfound(){
        Assertions.assertThrows(DataNotFoundException.class, () -> {
            locationService.getLocationById(4L);
        });
    }


    @Test
    public void test_getAllLocations(){
        String name = "comm 8";
        String city = "Tema";
        String country = "Ghana";
        Location location = locationService.addLocation(name, city,country);

        List<Location> locationList = locationService.getAllLocations();

        Assertions.assertEquals(locationList.size(), 1);
        Assertions.assertEquals(locationList.get(0).getName(), name);
        Assertions.assertEquals(locationList.get(0).getCity(), city);
        Assertions.assertEquals(locationList.get(0).getCountry(), country);
    }
}
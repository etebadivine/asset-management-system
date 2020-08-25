package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.dto.CreateLocationDto;
import com.financemobile.fmassets.model.Location;
import com.financemobile.fmassets.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/location")
@RestController
public class LocationController {

    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService){this.locationService = locationService;}

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createLocation(@RequestBody CreateLocationDto createLocationDto){
        Location location = locationService.addLocation(
                createLocationDto.getName(),
                createLocationDto.getCity(),
                createLocationDto.getCountry()
        );

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(location);
        return response;
    }

    @GetMapping
    public ApiResponse findAll(){
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(locationService.getAllLocations());
        return response;
    }
}

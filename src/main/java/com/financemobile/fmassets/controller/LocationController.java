package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.dto.CreateLocationDto;
import com.financemobile.fmassets.dto.EditCategoryDto;
import com.financemobile.fmassets.dto.EditLocationDto;
import com.financemobile.fmassets.model.Category;
import com.financemobile.fmassets.model.Location;
import com.financemobile.fmassets.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/location")
@RestController
public class LocationController {

    private LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse createLocation(@RequestBody @Valid CreateLocationDto createLocationDto) {
        Location location = locationService.addLocation(createLocationDto);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(location);
        return response;
    }

    @GetMapping
    public ApiResponse findAll() {
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(locationService.getAllLocations());
        return response;
    }


    @PutMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse editLocation(@RequestBody @Valid EditLocationDto editLocationDto) {

        Location location = locationService.editLocation(editLocationDto);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(location);

        return response;
    }

    @DeleteMapping(value = "/{id}")
    public ApiResponse removeLocation(@PathVariable Long id) {

        locationService.removeLocation(id);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData("Deleted");

        return response;

    }
}

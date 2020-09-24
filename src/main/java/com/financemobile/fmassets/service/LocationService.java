package com.financemobile.fmassets.service;

import com.financemobile.fmassets.dto.EditLocationDto;
import com.financemobile.fmassets.model.Location;

import java.util.List;

public interface LocationService {

    public Location addLocation(String name, String city, String country);
    public Location getLocationByName(String name);
    public Location getLocationById(Long id);
    public List<Location> getAllLocations();
    public Location editLocation(EditLocationDto editLocationDto);
    void removeLocation(Long id);
}

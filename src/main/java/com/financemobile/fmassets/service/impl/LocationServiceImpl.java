package com.financemobile.fmassets.service.impl;


import com.financemobile.fmassets.model.Location;
import com.financemobile.fmassets.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    @Override
    public Location addLocation(String name, String city, String country) {
        return null;
    }

    @Override
    public Location getLocationByName(String name) {
        return null;
    }

    @Override
    public Location getLocationById(Long id) {
        return null;
    }

    @Override
    public List<Location> getAllLocations() {
        return null;
    }
}

package com.financemobile.fmassets.service.impl;


import com.financemobile.fmassets.dto.CreateLocationDto;
import com.financemobile.fmassets.dto.EditLocationDto;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Location;
import com.financemobile.fmassets.repository.LocationRepository;
import com.financemobile.fmassets.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location addLocation(CreateLocationDto createLocationDto) {

        if(locationRepository.existsByName(createLocationDto.getName()))
            throw new AlreadyExistException("record already exists");

        Location location = new Location();
        location.setName(createLocationDto.getName());
        location.setCity(createLocationDto.getCity());
        location.setCountry(createLocationDto.getCountry());
        location.setCreatedBy("divine");
        return locationRepository.save(location);
    }

    @Override
    public Location getLocationByName(String name) {
        Optional<Location> locationOptional =
                locationRepository.findByName(name);

        if(locationOptional.isPresent()){
            return locationOptional.get();
        }

        throw new DataNotFoundException("record not found");
    }

    @Override
    public Location getLocationById(Long id) {
            Optional<Location> locationOptional = locationRepository.findById(id);

            if(locationOptional.isPresent()){
                return locationOptional.get();
            }

            throw new DataNotFoundException("record not found");
    }

    @Override
    public List<Location> getAllLocations() { return locationRepository.findAll(); }

    @Override
    public Location editLocation(EditLocationDto editLocationDto) {

        Optional<Location> locationOptional = locationRepository.findById(editLocationDto.getLocationId());
        if (locationOptional.isPresent()){
            Location location = locationOptional.get();
            location.setName(editLocationDto.getName());

            return locationRepository.save(location);
        }
        throw new DataNotFoundException("Location not found");
    }

    @Override
    public void removeLocation(Long id) {
        locationRepository.deleteById(id);
    }
}

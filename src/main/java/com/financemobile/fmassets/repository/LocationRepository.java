package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.Location;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.List;
import java.util.Optional;


public interface LocationRepository extends PagingAndSortingRepository<Location, Long>, JpaSpecificationExecutor<Location> {

            Boolean existsByName(String name);

    Optional<Location> findByName(String name);

    List<Location> findAll();
}

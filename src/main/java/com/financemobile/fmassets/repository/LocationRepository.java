package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.Location;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LocationRepository extends PagingAndSortingRepository<Location, Long>, JpaSpecificationExecutor<Location> {
}

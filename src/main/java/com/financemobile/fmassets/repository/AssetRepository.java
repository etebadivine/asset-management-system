package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.Asset;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Optional;


public interface AssetRepository extends PagingAndSortingRepository<Asset,Long>, JpaSpecificationExecutor<Asset> {

    Boolean existsByName(String name);
    Optional<Asset> findByName(String name);
}

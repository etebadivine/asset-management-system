package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.Asset;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AssetRepository extends PagingAndSortingRepository<Asset,Long>, JpaSpecificationExecutor<Asset> {

}

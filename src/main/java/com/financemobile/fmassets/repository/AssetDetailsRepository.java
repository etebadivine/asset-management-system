package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.AssetDetails;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface AssetDetailsRepository extends PagingAndSortingRepository<AssetDetails,Long>, JpaSpecificationExecutor<AssetDetails> {
}

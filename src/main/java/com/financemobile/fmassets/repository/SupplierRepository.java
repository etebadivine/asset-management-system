package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.Supplier;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends PagingAndSortingRepository<Supplier,Long>, JpaSpecificationExecutor<Supplier> {
    Boolean existsByName(String name);
    Optional<Supplier> findByName(String name);
    List<Supplier> findAll();
}

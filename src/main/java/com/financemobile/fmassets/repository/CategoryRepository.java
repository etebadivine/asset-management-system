package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends PagingAndSortingRepository<Category,Long>, JpaSpecificationExecutor<Category> {

    Boolean existsByName(String name);

    Optional<Category> findByName(String name);

    List<Category> findAll();

}

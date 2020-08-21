package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.Category;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category,Long>, JpaSpecificationExecutor<Category> {

}

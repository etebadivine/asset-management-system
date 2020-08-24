package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends PagingAndSortingRepository<Department,Long>, JpaSpecificationExecutor<Department> {

    Optional<Department> findByName(String name);

    List<Department> findAll();
}

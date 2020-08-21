package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentRepository extends PagingAndSortingRepository<Department,Long>, JpaSpecificationExecutor<Department> {

}

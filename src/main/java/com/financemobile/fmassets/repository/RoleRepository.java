package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RoleRepository extends PagingAndSortingRepository<Role,Long>, JpaSpecificationExecutor<Role> {
}

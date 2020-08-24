package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.model.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends PagingAndSortingRepository<Role,Long>, JpaSpecificationExecutor<Role> {

    Boolean existsByName (String name);

    Optional<Role> findById(Long id);

    List<Role> findAll();
}

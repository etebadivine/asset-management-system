package com.financemobile.fmassets.repository;


import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Optional;


public interface UserRepository extends PagingAndSortingRepository<User,Long>, JpaSpecificationExecutor<User> {

     Optional<User> findByEmail(String email);

     Boolean existsByEmail(String email);


}

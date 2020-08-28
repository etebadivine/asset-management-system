package com.financemobile.fmassets.repository;

import com.financemobile.fmassets.dto.UpdateUserStatusDto;
import com.financemobile.fmassets.enums.UserStatus;
import com.financemobile.fmassets.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;


public interface UserRepository extends PagingAndSortingRepository<User,Long>, JpaSpecificationExecutor<User> {
    Boolean existsById(long id);

     Optional<User> findById(Long id);

    boolean findById(UpdateUserStatusDto updateUserStatusDto);

}

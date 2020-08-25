package com.financemobile.fmassets.service;

import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.UserSpec;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<User> searchUsers(UserSpec userSpec, Pageable pageable);
}

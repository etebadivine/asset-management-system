package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.UserSpec;
import com.financemobile.fmassets.repository.UserRepository;
import com.financemobile.fmassets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> searchUsers(UserSpec userSpec, Pageable pageable) {
        List<User> userList = new ArrayList<>();
        Page<User> usersPage = userRepository.findAll(userSpec, pageable);
        if(usersPage.hasContent())
            return usersPage.getContent();
        return null;
    }
}

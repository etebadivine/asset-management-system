package com.financemobile.fmassets.service.impl;

import com.financemobile.fmassets.dto.UpdateUserStatusDto;
import com.financemobile.fmassets.exception.DataNotFoundException;
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

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> searchUsers(UserSpec userSpec, Pageable pageable) {
        List<User> userList = new ArrayList<>();
        Page<User> usersPage = userRepository.findAll(userSpec, pageable);
        if (usersPage.hasContent())
            return usersPage.getContent();
        return null;
    }

    @Override
    public User UpdateStatus(UpdateUserStatusDto updateUserStatusDto) throws DataNotFoundException {
        if (userRepository.findById(updateUserStatusDto))
            throw new DataNotFoundException("record not found");
        User user = new User();
        user.setId(updateUserStatusDto.getId());
        user.setStatus(updateUserStatusDto.getStatus());
        return userRepository.save(user);
    }
}








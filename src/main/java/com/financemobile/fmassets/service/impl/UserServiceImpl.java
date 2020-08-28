package com.financemobile.fmassets.service.impl;


import com.financemobile.fmassets.dto.CreateUserDto;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
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
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(CreateUserDto createUserDto) {

        if(userRepository.existsByEmail(createUserDto.getEmail())){
            throw new AlreadyExistException("User already exist");
        }

        User user = new User();
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setEmail(createUserDto.getEmail());
        user.setPhone(createUserDto.getPhone());
        user.setPassword(createUserDto.getPassword());

        return userRepository.save(user);
    }


    @Override
    public List<User> searchUsers(UserSpec userSpec, Pageable pageable) {
        List<User> userList = new ArrayList<>();
        Page<User> usersPage = userRepository.findAll(userSpec, pageable);
        if(usersPage.hasContent())
            return usersPage.getContent();
        return userList;
    }

    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent()){
            return userOptional.get();
        }

        throw new DataNotFoundException("record not found");
    }

    @Override
    public User updateStatus(UpdateUserStatusDto updateUserStatusDto) {

        Optional<User> userOptional =
                userRepository.findById(updateUserStatusDto.getUserId());

        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setStatus(updateUserStatusDto.getStatus());
            return userRepository.save(user);
        }

        throw new DataNotFoundException("record not found");
    }
}

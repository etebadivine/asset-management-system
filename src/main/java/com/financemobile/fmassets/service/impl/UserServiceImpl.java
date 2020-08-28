package com.financemobile.fmassets.service.impl;


import com.financemobile.fmassets.dto.CreateUserDto;
import com.financemobile.fmassets.enums.UserStatus;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.model.Role;
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
    public List<User> searchUsers(UserSpec userSpec, Pageable pageable) {
        List<User> userList = new ArrayList<>();
        Page<User> usersPage = userRepository.findAll(userSpec, pageable);
        if(usersPage.hasContent())
            return usersPage.getContent();
        return userList;
    }

//    @Override
//    public User getUserByEmail(String email) {
//        User user = new User();
//        user.setId(200L);
//        user.setFirstName("Atta");
//        user.setLastName("Dwoa");
//        user.setEmail("me@gmail.com");
//        user.setPhone("+233241428119");
//        user.setStatus(UserStatus.ACTIVE);
////        user.setDepartment(department);
////        user.setRole(role);
//        return user;
//
////        System.out.println("getUserByEmail: " + email);
//        Optional<User> userOptional = userRepository.findByEmail(email);
//
//
////        System.out.println("userOptional: " + userOptional);
//        if (userOptional.isPresent()){
//            return userOptional.get();
//        }
////        System.out.println("userOptional noT NOT");
//        throw new DataNotFoundException("record not found");
//    }

    @Override
    public User getUserByEmail(String email) throws DataNotFoundException {
        Optional<User> userOptional =userRepository.findByEmail(email);

        if(userOptional.isPresent()){
            return userOptional.get();
        }

        throw new DataNotFoundException("role not found");
    }

}

package com.financemobile.fmassets.service.impl;


import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.exception.PasswordMismatchException;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.UserSpec;
import com.financemobile.fmassets.repository.RoleRepository;
import com.financemobile.fmassets.repository.UserRepository;
import com.financemobile.fmassets.service.RoleService;
import com.financemobile.fmassets.service.UserService;
import com.financemobile.fmassets.service.messaging.EmailComposer;
import com.financemobile.fmassets.service.messaging.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailComposer emailComposer;


    @Override
    public User addUser(CreateUserDto createUserDto) {

        if (userRepository.existsByEmail(createUserDto.getEmail())) {
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
        if (usersPage.hasContent())
            return usersPage.getContent();
        return userList;
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        throw new DataNotFoundException("user not found");
    }


    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        throw new DataNotFoundException("record not found");
    }

    @Override
    public User updateStatus(UpdateUserStatusDto updateUserStatusDto) {

        Optional<User> userOptional =
                userRepository.findById(updateUserStatusDto.getUserId());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setStatus(updateUserStatusDto.getStatus());
            return userRepository.save(user);
        }

        throw new DataNotFoundException("record not found");
    }

    @Override
    public User resetPassword(ResetPasswordDto resetPasswordDto) {
        Optional<User> userOptional =
                userRepository.findById(resetPasswordDto.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(resetPasswordDto.getOldPassword())) {
                user.setPassword(resetPasswordDto.getNewPassword());
                return userRepository.save(user);
            }
            throw new PasswordMismatchException("password mismatch");
        }
        throw new DataNotFoundException("user not found");
    }

    @Override
    public Boolean forgotPassword(ForgotPasswordDto forgotPasswordDto) {

        if (!userRepository.existsByEmail(forgotPasswordDto.getEmail())) {
            throw new DataNotFoundException("email user does not exist");
        }

        User user = getUserByEmail(forgotPasswordDto.getEmail());
        Boolean sent = false;

        Map<String, Object> params = new HashMap<>();
        params.put("firstName", user.getFirstName());

        String content = emailComposer.composeMessageContent(
                params,"forgot_password.vm"
        );

        EmailMessageDto emailMessageDto =
                new EmailMessageDto(
                        forgotPasswordDto.getEmail(), null,
                        "Forgot Password", content
                );

        try {
            sent = sendEmailService.send(emailMessageDto);
        } catch (MessagingException mex){

        }
        return sent;
    }

    @Override
    public User updateUserRole(UpdateUserRoleDto updateuserRoleDto) {
        User user = getUserById(updateuserRoleDto.getUserId());
        Optional<Role> roleOptional = roleRepository.findByName(updateuserRoleDto.getRole());

        if (roleOptional.isPresent()) {
            user.setRole(roleOptional.get());
        } else throw new DataNotFoundException("role not found");

        return userRepository.save(user);
    }
}









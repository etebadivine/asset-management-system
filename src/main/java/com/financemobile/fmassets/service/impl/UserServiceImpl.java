package com.financemobile.fmassets.service.impl;


import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.enums.UserStatus;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.exception.PasswordMismatchException;
import com.financemobile.fmassets.model.Department;
import com.financemobile.fmassets.model.Role;
import com.financemobile.fmassets.model.Supplier;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.UserSpec;
import com.financemobile.fmassets.repository.RoleRepository;
import com.financemobile.fmassets.repository.UserRepository;
import com.financemobile.fmassets.service.DepartmentService;
import com.financemobile.fmassets.service.RoleService;
import com.financemobile.fmassets.service.UserService;
import com.financemobile.fmassets.service.messaging.EmailComposer;
import com.financemobile.fmassets.service.messaging.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmailComposer emailComposer;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
        user.setStatus(UserStatus.ACTIVE);
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
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

        throw new DataNotFoundException("User not found");
    }


    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        throw new DataNotFoundException("User not found");
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

        throw new DataNotFoundException("User not found");
    }

    @Override
    public User resetPassword(ResetPasswordDto resetPasswordDto) {
        Optional<User> userOptional =
                userRepository.findById(resetPasswordDto.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
                return userRepository.save(user);
            }
            throw new PasswordMismatchException("password mismatch");
        }
        throw new DataNotFoundException("User not found");
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
                params, "forgot_password.vm"
        );

        EmailMessageDto emailMessageDto =
                new EmailMessageDto(
                        forgotPasswordDto.getEmail(), null,
                        "Forgot Password", content
                );

        try {
            sent = sendEmailService.send(emailMessageDto);
        } catch (MessagingException mex) {

        }
        return sent;
    }

    @Override
    public User updateUserRole(UpdateUserRoleDto updateuserRoleDto) {
        User user = getUserById(updateuserRoleDto.getUserId());
        Optional<Role> roleOptional = roleRepository.findByName(updateuserRoleDto.getRole());

        if (roleOptional.isPresent()) {
            user.setRole(roleOptional.get());
        } else throw new DataNotFoundException("Role not found");

        return userRepository.save(user);
    }

    @Override
    public Boolean sendUserInvite(UserInviteDto userInviteDto) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (userRepository.existsByEmail(userInviteDto.getEmail())) {
            throw new AlreadyExistException("email user already exist");
        }

        Boolean sent = false;

        Map<String, Object> params = new HashMap<>();
        params.put("email",userInviteDto.getEmail());
        params.put("firstName",userInviteDto.getName());
        params.put("adminName",authentication.getName());

        String content = emailComposer.composeMessageContent(
                params, "user_invite.vm");
        EmailMessageDto emailMessageDto =
                new EmailMessageDto(
                        userInviteDto.getEmail(), null,
                        "User Invite", content
                );
        try {
            sent = sendEmailService.send(emailMessageDto);
        } catch (MessagingException mex) {

        }
        return sent;
    }

    @Override
    public User editUser(EditUserDto editUserDto) {

        Optional<User> userOptional = userRepository.findById(editUserDto.getUserId());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            user.setFirstName(editUserDto.getFirstName());
            user.setLastName(editUserDto.getLastName());
            user.setEmail(editUserDto.getEmail());
            user.setPhone(editUserDto.getPhone());
            Department department = departmentService.getDepartmentByName(editUserDto.getDepartment());
            user.setDepartment(department);
            Role role = roleService.getRoleByName(editUserDto.getRole());
            user.setRole(role);

            return userRepository.save(user);
        }
        throw new DataNotFoundException("User not found");
    }

    @Override
    public void removeUser(Long id) {
        userRepository.deleteById(id);
    }
}









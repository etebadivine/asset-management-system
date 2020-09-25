package com.financemobile.fmassets.service;


import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.UserSpec;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {

    List<User> searchUsers(UserSpec userSpec, Pageable pageable);
    User addUser(CreateUserDto createUserDto);
    User getUserById(Long id);
    User getUserByEmail(String email);
    User resetPassword(ResetPasswordDto resetPasswordDto);
    User updateStatus(UpdateUserStatusDto updateUserStatusDto);
    Boolean forgotPassword(ForgotPasswordDto forgotPasswordDto);
    User updateUserRole(UpdateUserRoleDto updateuserRoleDto);
    Boolean sendUserInvite(UserInviteDto userInviteDto);
    User editUser(EditUserDto editUserDto);
    void removeUser(Long id);
}



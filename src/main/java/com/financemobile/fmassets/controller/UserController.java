package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.dto.CreateUserDto;
import com.financemobile.fmassets.dto.FindByEmailDto;
import com.financemobile.fmassets.dto.UpdateUserStatusDto;
import com.financemobile.fmassets.dto.ResetPasswordDto;
import com.financemobile.fmassets.dto.*;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.UserSpec;
import com.financemobile.fmassets.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequestMapping("/user")
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addUser(@RequestBody @Valid CreateUserDto createUserDto) {
        User user = userService.addUser(createUserDto);
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(user);
        return response;
    }

    @GetMapping("/{userId}")
    public ApiResponse getUserById(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(user);
        return response;
    }

    @GetMapping
    public ApiResponse searchUsers(UserSpec userSpec, Pageable pageable) {

        List<User> userList = userService.searchUsers(userSpec, pageable);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setData(userList);
        response.setMessage("Success");
        return response;
    }

    @PostMapping("/one")
    public ApiResponse findByEmail(@RequestBody FindByEmailDto findByEmailDto) {

        User user = userService.getUserByEmail(
                findByEmailDto.getEmail()
        );

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(user);
        return response;
    }

    @PostMapping(value = "/password-reset")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {

        User user = userService.resetPassword(resetPasswordDto);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(user);
        return response;
    }

    @PostMapping(value = "/status")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse updateStatus(@RequestBody UpdateUserStatusDto updateUserStatusDto) {

        User user = userService.updateStatus(updateUserStatusDto);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(user);
        return response;
    }

    @PostMapping(value = "/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto){

        Boolean user = userService.forgotPassword(forgotPasswordDto);

        // To Do:reset by email
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(user);
        return response;
    }

    @PostMapping(value = "/role")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse updateRole(@RequestBody UpdateUserRoleDto updateuserRoleDto) {

        User user = userService.updateUserRole(updateuserRoleDto);
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(user);
        return response;
    }

    @PostMapping(value = "/invite")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse sendUserInvite(@RequestBody @Valid  UserInviteDto userInviteDto){

        Boolean sent = userService.sendUserInvite(userInviteDto);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(sent);
        return response;
    }
}

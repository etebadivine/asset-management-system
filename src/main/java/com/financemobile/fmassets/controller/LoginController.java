package com.financemobile.fmassets.controller;


import com.financemobile.fmassets.dto.AccessTokenDto;
import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.dto.CreateUserDto;
import com.financemobile.fmassets.dto.LoginDto;
import com.financemobile.fmassets.exception.AuthenticationException;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.service.UserService;
import com.financemobile.fmassets.service.impl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/auth")
@RestController
public class LoginController {


    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenStore tokenStore;

    @PostMapping("/login")
    public ApiResponse login(@RequestBody @Valid LoginDto loginDto){
        AccessTokenDto accessTokenDto =userDetailService.login(loginDto.getUsername(), loginDto.getPassword());
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(accessTokenDto);
        return response;
    }

    @GetMapping("/logout")
    public ApiResponse logout(@RequestParam("token") String token){

        OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);

        if (accessToken == null) {
            throw new AuthenticationException("session not found.");
        }

        tokenStore.removeAccessToken(accessToken);
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(true);
        return response;
    }

    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse addUser(@RequestBody @Valid CreateUserDto createUserDto) {
        User user = userService.addUser(createUserDto);
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Success");
        response.setData(user);
        return response;
    }
}

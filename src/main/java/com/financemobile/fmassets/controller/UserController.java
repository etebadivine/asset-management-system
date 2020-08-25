package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.model.User;
import com.financemobile.fmassets.querySpec.UserSpec;
import com.financemobile.fmassets.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
}

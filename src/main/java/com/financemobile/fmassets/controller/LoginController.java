package com.financemobile.fmassets.controller;


import com.financemobile.fmassets.dto.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class LoginController {


    @PostMapping("/login")
    public ApiResponse login(){
        return null;
    }


    @PostMapping("/logout")
    public ApiResponse logout(){
        return null;
    }

}

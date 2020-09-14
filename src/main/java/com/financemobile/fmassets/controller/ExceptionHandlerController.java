package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.exception.AlreadyExistException;
import com.financemobile.fmassets.exception.AuthenticationException;
import com.financemobile.fmassets.exception.DataNotFoundException;
import com.financemobile.fmassets.exception.PasswordMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DataNotFoundException.class)
    public ApiResponse handleDataNotfound(DataNotFoundException dnfe){
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("failed");
        response.setData(dnfe.getMessage());
        return response;
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ApiResponse handleAlreadyExist(AlreadyExistException aee){
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("failed");
        response.setData(aee.getMessage());
        return response;
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public ApiResponse handlePasswordMismatch(PasswordMismatchException pme){
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("failed");
        response.setData(pme.getMessage());
        return response;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ApiResponse handleAuth(AuthenticationException ae){
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("failed");
        response.setData(ae.getMessage());
        return response;
    }

}

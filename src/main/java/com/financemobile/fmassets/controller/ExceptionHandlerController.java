package com.financemobile.fmassets.controller;

import com.financemobile.fmassets.dto.ApiResponse;
import com.financemobile.fmassets.exception.DataNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(DataNotFoundException.class)
    public ApiResponse handleDataNotfound(DataNotFoundException dnfe){
        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage(dnfe.getMessage());
        response.setData(null);
        return response;
    }

}

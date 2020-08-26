package com.financemobile.fmassets.dto;

import lombok.Data;

@Data
public class ApiResponse {

    private boolean status;
    private String message;
    private Object data;
}

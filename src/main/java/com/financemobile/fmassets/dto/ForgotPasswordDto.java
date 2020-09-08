package com.financemobile.fmassets.dto;


import lombok.Data;

import javax.validation.constraints.Email;


@Data
public class ForgotPasswordDto {
    @Email
    private String email;
}

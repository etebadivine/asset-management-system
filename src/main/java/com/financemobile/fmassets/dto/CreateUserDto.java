package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class CreateUserDto {

    @NotBlank
    private String email;


}

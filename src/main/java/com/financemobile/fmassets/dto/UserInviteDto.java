package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class UserInviteDto {

    @NotBlank
    private String email;

    @NotBlank
    private String name;
}

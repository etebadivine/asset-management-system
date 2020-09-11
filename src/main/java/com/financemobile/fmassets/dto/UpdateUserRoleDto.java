package com.financemobile.fmassets.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UpdateUserRoleDto {

    @NotNull
    private Long userId;

    @NotBlank
    private String role;
}

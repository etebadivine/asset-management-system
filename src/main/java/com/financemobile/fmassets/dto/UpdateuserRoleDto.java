package com.financemobile.fmassets.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateuserRoleDto {

    @NotBlank
    private String userId;
    @NotBlank
    private String role;

}

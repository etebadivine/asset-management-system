package com.financemobile.fmassets.dto;


import com.financemobile.fmassets.model.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserRoleDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private String role;
}

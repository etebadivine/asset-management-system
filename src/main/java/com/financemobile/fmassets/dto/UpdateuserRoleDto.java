package com.financemobile.fmassets.dto;


import com.financemobile.fmassets.model.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateuserRoleDto {

    @NotBlank
    private Long userId;
    @NotBlank
    private Role role;

    public UpdateuserRoleDto() {
    }
}

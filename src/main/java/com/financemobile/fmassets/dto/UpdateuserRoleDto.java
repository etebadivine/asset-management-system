package com.financemobile.fmassets.dto;


import com.financemobile.fmassets.model.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateuserRoleDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private String role;

    public UpdateuserRoleDto(@NotBlank Long userId, @NotBlank String role) {
        this.userId = userId;
        this.role = role;
    }

    public UpdateuserRoleDto() {
    }
}

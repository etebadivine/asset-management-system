package com.financemobile.fmassets.dto;

import com.financemobile.fmassets.enums.UserStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserStatusDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private UserStatus status;
}


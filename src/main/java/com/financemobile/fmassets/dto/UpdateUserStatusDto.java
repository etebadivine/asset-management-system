package com.financemobile.fmassets.dto;

import com.financemobile.fmassets.enums.UserStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateUserStatusDto {

    @NotBlank
    private long Id;

    @NotBlank
    private UserStatus status;
}


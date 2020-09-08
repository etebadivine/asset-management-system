package com.financemobile.fmassets.dto;

import com.financemobile.fmassets.enums.UserStatus;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class UpdateUserStatusDto {

    @NotNull
    private Long userId;

    @NotNull
    private UserStatus status;

    public UpdateUserStatusDto(Long userId, UserStatus status) {
        this.userId = userId;
        this.status = status;
    }

    public UpdateUserStatusDto() {
    }
}


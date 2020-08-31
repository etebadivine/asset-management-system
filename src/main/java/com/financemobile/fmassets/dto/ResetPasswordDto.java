package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class ResetPasswordDto {
    @NotBlank
    private Long userId;
    @NotBlank
    private String oldPassword;
    @NotBlank
    private String newPassword;

    public ResetPasswordDto(@NotBlank Long userId, @NotBlank String oldPassword, @NotBlank String newPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public ResetPasswordDto() {
    }
}

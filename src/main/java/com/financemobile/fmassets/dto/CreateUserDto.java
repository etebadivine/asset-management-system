package com.financemobile.fmassets.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;


@Data
public class CreateUserDto {

    private String firstName;

    private String lastName;

    @NotBlank
    private String email;

    private String phone;

    @NotBlank
    private String password;

    public CreateUserDto(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String email, @NotBlank String phone, @NotBlank String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public CreateUserDto() {
    }
}
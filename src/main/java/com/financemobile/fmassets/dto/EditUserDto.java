package com.financemobile.fmassets.dto;


import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class EditUserDto {

        @NotNull
        private Long userId;
        @NotBlank
        private String firstName;
        @NotBlank
        private String lastName;
        @NotBlank
        private String email;
        @NotBlank
        private String phone;
        @NotBlank
        private String department;
        @NotBlank
        private String role;
        @NotBlank
        private String password;

        public EditUserDto() {
        }
}

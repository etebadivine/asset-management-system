package com.financemobile.fmassets.dto;

import com.financemobile.fmassets.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateUserDto {

    @NotBlank
    private String email;


    public CreateUserDto(String email) {
        this.email = email;
    }
}

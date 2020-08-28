package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class FindByEmailDto {
    @NotBlank
    private String email;
}

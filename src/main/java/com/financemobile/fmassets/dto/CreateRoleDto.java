package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateRoleDto {
    @NotBlank
    private String name;
    private Long id;
}

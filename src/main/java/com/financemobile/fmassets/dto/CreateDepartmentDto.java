package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class CreateDepartmentDto {
    @NotBlank
    private String name;
    private Long id;
}

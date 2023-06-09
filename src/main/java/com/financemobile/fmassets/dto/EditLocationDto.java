package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class EditLocationDto {

    @NotNull
    private Long locationId;
    @NotBlank
    private String name;
    @NotBlank
    private String city;
    @NotBlank
    private String country;

    public EditLocationDto() {
    }
}

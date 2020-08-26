package com.financemobile.fmassets.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;


@Data
public class CreateCategoryDto {

    @NotBlank
    private String name;
    private String description;
}

package com.financemobile.fmassets.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class EditCategoryDto {

    @NotNull
    private Long categoryId;
    @NotBlank
    private String name;
    private String description;

    public EditCategoryDto() {
    }
}

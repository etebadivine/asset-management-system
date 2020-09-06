package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class EditAssetDto {

    @NotBlank
    private String name;
    @NotBlank
    private String location;
    @NotBlank
    private String supplier;
    @NotBlank
    private String department;

    @NotBlank
    private String category;
    private Long userId;

    public EditAssetDto(@NotBlank String name, @NotBlank String location, @NotBlank String supplier, @NotBlank String department, @NotBlank String category, Long userId) {
        this.name = name;
        this.location = location;
        this.supplier = supplier;
        this.department = department;
        this.category = category;
        this.userId = userId;
    }

    public EditAssetDto() {
    }
}

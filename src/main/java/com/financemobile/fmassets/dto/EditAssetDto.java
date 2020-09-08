package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class EditAssetDto {

    @NotNull
    private Long assetId;
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

    public EditAssetDto() {
    }
}

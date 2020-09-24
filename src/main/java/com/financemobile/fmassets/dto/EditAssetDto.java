package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


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
    private String make;
    private String color;
    private String model;
    private String manufacturer;
    private String serialNumber;
    private String imageBytes;
    private Date purchasedDate;
    private String warranty;
    private String licenses;
    private Long userId;

    public EditAssetDto() {
    }
}

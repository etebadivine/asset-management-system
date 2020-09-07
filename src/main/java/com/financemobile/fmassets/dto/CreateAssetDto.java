package com.financemobile.fmassets.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;


@Data
public class CreateAssetDto {

    @NotBlank
    private String name;
    @NotBlank
    private String location;
    @NotBlank
    private String supplier;
    @NotBlank
    private String department;

    private String make;
    private String color;
    private String model;
    @NotBlank
    private String category;
    private String manufacturer;
    private String serialNumber;
    private String imageBytes;
    private String warranty;
    private String licenses;
    private Long userId;
    private Map<String, String> metaData;

    public CreateAssetDto(@NotBlank String name, @NotBlank String location, @NotBlank String supplier, @NotBlank String department, @NotBlank String category) {
        this.name = name;
        this.location = location;
        this.supplier = supplier;
        this.department = department;
        this.category = category;
    }

    public CreateAssetDto() {
    }
}

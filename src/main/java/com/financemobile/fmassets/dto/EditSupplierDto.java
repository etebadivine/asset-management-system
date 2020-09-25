package com.financemobile.fmassets.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class EditSupplierDto {

    @NotNull
    private Long supplierId;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String telephone;
    @NotBlank
    private String mobile;


    public EditSupplierDto() {
    }
}

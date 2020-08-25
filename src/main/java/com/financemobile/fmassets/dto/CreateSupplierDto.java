package com.financemobile.fmassets.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateSupplierDto {

    @NotBlank
    private String name;
    private String address;
    private String telephone;
    private String mobile;


    public CreateSupplierDto(String name, String address, String telephone, String mobile) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.mobile = mobile;
    }
}

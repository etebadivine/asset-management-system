package com.financemobile.fmassets.dto;


import lombok.Data;

@Data
public class CreateSupplierDto {


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

    public CreateSupplierDto(){}
}

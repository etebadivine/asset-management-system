package com.financemobile.fmassets.model;

import lombok.Data;

import java.util.Date;

@Data
public class AssetDetails {

    private Long id;
    private String model;
    private String manufacturer;
    private Long serialNumber;
    private String warranty;
    private String licenses;
    private Date purchasedDate;
    private String createdBy;
    private Date dateCreated;
    private Date dateModified;
}

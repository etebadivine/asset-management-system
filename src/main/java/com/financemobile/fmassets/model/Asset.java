package com.financemobile.fmassets.model;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

@Data
public class Asset {


    private Long id;
    private String model;
    private String status;
    private String description;
    private String createdBy;
    private Date dateCreated;
    private Date dateModified;
    private Category category;
    private AssetDetails assetDetails;
    private Department department;
    private Location location;


}

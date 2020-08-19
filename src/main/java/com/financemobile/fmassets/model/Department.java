package com.financemobile.fmassets.model;

import lombok.Data;

import java.util.Date;

@Data
public class Department {

    private Long id;
    private String name;
    private Long numberOfAssets;
    private String createdBy;
    private Date dateCreated;
    private Date dateModified;

}

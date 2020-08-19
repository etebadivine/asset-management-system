package com.financemobile.fmassets.model;

import lombok.Data;

import java.util.Date;

@Data
public class Category {

    private Long id;
    private String name;
    private String description;
    private String createdBy;
    private Date dateCreated;
    private Date dateModified;
}

package com.financemobile.fmassets.model;

import lombok.Data;

import java.util.Date;

@Data
public class Location {

    private Long id;
    private String name;
    private String city;
    private String country;
    private String createdBy;
    private Date dateCreated;
    private Date dateModified;


}

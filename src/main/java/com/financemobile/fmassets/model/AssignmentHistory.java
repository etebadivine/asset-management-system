package com.financemobile.fmassets.model;

import lombok.Data;

import java.util.Date;

@Data
public class AssignmentHistory {

    private Long id;
    private String createdBy;
    private Date dateCreated;
    private Date dateModified;
    private Asset asset;
    private Employee employee;

}


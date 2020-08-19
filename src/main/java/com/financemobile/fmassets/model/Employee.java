package com.financemobile.fmassets.model;

import lombok.Data;

import java.util.Date;

@Data
public class Employee {

    private Long id;
    private String name;
    private String status;
    private String email;
    private Long telephoneNumber;
    private String createdBy;
    private Date dateCreated;
    private Date dateModified;
    private Department department;




}

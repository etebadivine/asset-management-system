package com.financemobile.fmassets.model;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.Date;

@Data
public class AssignmentHistory {

    private Long id;
    private String createdBy;
    private Date dateCreated;
    private Date dateModified;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asset_id")
    private Asset asset;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

}
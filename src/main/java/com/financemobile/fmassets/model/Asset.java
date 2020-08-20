package com.financemobile.fmassets.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class Asset {


    private Long id;
    private String model;
    private String status;
    private String description;
    private String createdBy;
    private Date dateCreated;
    private Date dateModified;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asset_detail_id")
    private AssetDetails assetDetails;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToOne(mappedBy = "Asset", fetch = FetchType.LAZY)
    private Set<AssignmentHistory> assignmentHistories = new HashSet<>();


}

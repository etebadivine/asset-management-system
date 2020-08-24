package com.financemobile.fmassets.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;



@Data
@Entity
@Table(name = "asset_details")
public class AssetDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String warranty;
    private String licenses;
    private String model;
    private String manufacturer;
    private String color;
    @Column(name = "serial_number")
    private Long serialNumber;
    @Column(name = "image_bytes")
    private String imageBytes;
    @Column(name = "purchased_date")
    private Date purchasedDate;
    @Column(name = "created_by")
    private String createdBy;
    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name = "date_modified")
    private Date dateModified;

    @OneToOne(mappedBy = "assetDetails", fetch = FetchType.LAZY)
    private Asset assets;
}
package com.financemobile.fmassets.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "AssetDetails")
public class AssetDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private Long serialNumber;
    private String warranty;
    private String licenses;
    private String model;
    private String createdBy;
    private String manufacturer;
    private String colorField;

  @CreationTimestamp
  @Column(name = "date_created")
  private Date dateCreated;
  @Column(name = "purchased_date")
  private Date purchasedDate;
  @UpdateTimestamp
  @Column(name = "date_modified")
  private Date dateModified;

}

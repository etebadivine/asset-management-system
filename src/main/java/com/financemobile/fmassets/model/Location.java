package com.financemobile.fmassets.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Location")
public class Location {
    @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String city;
    private String country;
    private String createdBy;
    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name = "date_modified")
    private Date dateModified;


}

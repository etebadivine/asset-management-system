package com.financemobile.fmassets.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "Department")
public class Department {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long numberOfAssets;
    private String createdBy;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name = "date_modified")
    private Date dateModified;

}

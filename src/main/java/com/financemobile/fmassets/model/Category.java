package com.financemobile.fmassets.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    @Column(name = "created_by")
    private String createdBy;
    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name = "date_modified")
    private Date dateModified;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private Set<Asset> assets = new HashSet<>();

}
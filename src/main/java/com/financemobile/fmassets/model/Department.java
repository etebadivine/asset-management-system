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
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(name = "number_of_assets")
    private Long numberOfAssets = 0L;
    @Column(name = "created_by")
    private String createdBy;
    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name = "date_modified")
    private Date dateModified;

    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<Asset> assets = new HashSet<>();

}
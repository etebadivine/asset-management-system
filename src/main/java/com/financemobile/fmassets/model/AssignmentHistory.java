package com.financemobile.fmassets.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "assignment_history")
public class AssignmentHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "asset_id", nullable = false)
    private Asset asset;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "created_by")
    private String createdBy;
    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name = "date_modified")
    private Date dateModified;

}
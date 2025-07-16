package com.example.aadhiStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report_master")
public class ReportMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_name")
    private String reportName;

    @Column(name = "description")
    private String description;

    @Column(name = "screen_name")
    private String screenName;

    @Lob
    @Column(name = "jrxml_file")
    private byte[] jrxmlFile;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(nullable = true)
    private byte[] reportFile;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private Set<ReportParameter> parameters;
}

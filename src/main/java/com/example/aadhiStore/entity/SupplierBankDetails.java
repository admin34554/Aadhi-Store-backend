package com.example.aadhiStore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "supplier_bank_details")
@Getter
@Setter
@NoArgsConstructor
public class SupplierBankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierMaster supplier;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "branch")
    private String branch;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "remarks")
    private String remarks;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "supplier_master_id")
    private SupplierMaster supplierMaster;
}

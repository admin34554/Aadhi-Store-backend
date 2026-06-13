package com.example.aadhiStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tax_master")
public class TaxMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "hsn_code", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private String hsnCode;

    @Column(name = "name")
    private String name;

    @Column(name = "sales_tax_percentage")
    private Double salestaxPercentage;

    @Column(name = "charge_percentage")
    private Double chargePercentage;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "cgst")
    private String cgst;

    @Column(name = "sgst")
    private String sgst;

    @Column(name = "igst")
    private String igst;
}

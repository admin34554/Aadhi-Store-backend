package com.example.aadhiStore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tax_master_new")
@Getter
@Setter
@NoArgsConstructor
public class TaxMasterNew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country")
    private String country;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "state")
    private String state;

    @Column(name = "state_gst_code")
    private String stateGstCode;

    @Column(name = "hsn_code")
    private String hsnCode;

    @Column(name = "hsn_description")
    private String hsnDescription;

    @Column(name = "product_group_id")
    private Long productGroupId;

    @Column(name = "product_group_descp")
    private String productGroupDescp;

    @Column(name = "product_brand_id")
    private Long productBrandId;

    @Column(name = "product_brand_name")
    private String productBrandName;

    @Column(name = "unit")
    private String unit;

    @Column(name = "unit_text")
    private String unitText;

    @Column(name ="cgst")
    private Double cgst;

    @Column(name = "sgst")
    private Double sgst;

    @Column(name = "igst")
    private Double igst;


}

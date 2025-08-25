package com.example.aadhiStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_bill_master")
public class PurchaseBillMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "purchase_type")
    private String purchaseType;

    @Column(name = "purchase_bill_no")
    private String billNo;

    @Column(name = "purchase_bill_date")
    private Date billDate;

    @Column(name = "customer_bill_date")
    private Date cusBillDate;

    @Column(name = "po_no")
    private String poNo;

    @Column(name = "broker_name")
    private String brokerName;

    @Column(name = "prod_id")
    private Long prodId;

    @Column(name = "prod_code")
    private String prodCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "po_lp_rate")
    private String poRate;

    @Column(name = "balance_quantity")
    private double balanceQuantity;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "rate")
    private Long rate;

    @Column(name = "br_common")
    private double brCommon;

    @Column(name = "br_common_total")
    private double brCommonTotal;

}

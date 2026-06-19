package com.example.aadhiStore.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cash_bill_item")
public class CashBillItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "cash_bill_id")
    private CashBill cashBill;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "tax")
    private String tax;

    @Column(name = "total")
    private Double total;

    @Column(name = "br_no")
    private String brNo;

    @Column(name = "sur_charge")
    private String surCh;


}

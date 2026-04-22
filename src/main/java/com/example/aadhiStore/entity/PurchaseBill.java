package com.example.aadhiStore.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_bill")
public class PurchaseBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "purchase_type")
    private String purchaseType;

    @Column(name = "purchase_bill_no", insertable = false, updatable = false)
    @Generated(GenerationTime.INSERT)
    private String purchaseBillNo;

    @Column(name = "po_no")
    private String poNo;

    @ManyToOne
    @JoinColumn(name = "broker_id")
    private BrokerMaster brokerId;

    @Column(name = "purchase_bill_date")
    private Date purchaseBillDate;

    @Column(name = "cust_bill_date")
    private Date custBillDate;

    @OneToMany(mappedBy = "purchaseBill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItem> items;

    @Column(name = "remarks")
    private String remarks;
}

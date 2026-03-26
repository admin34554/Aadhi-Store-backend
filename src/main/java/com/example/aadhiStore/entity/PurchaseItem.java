package com.example.aadhiStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase_item")
public class PurchaseItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code")
    private String productCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "polp_rate")
    private Long pRate;

    @Column(name = "balance_quantity")
    private Double balanceQuantity;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "br_comm")
    private Double brComm;

    @Column(name = "br_total")
    private Double brTotal;

    @ManyToOne
    @JoinColumn(name = "purchase_bill_id")
    private PurchaseBill purchaseBill;

}

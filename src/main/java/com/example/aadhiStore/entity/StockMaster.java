package com.example.aadhiStore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "stock_master")
public class StockMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "hsn_code")
    private String hsnCode;

    @Column(name = "mrp")
    private Double mrp;

    @Column(name = "msp")
    private Double msp;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "product_item_price_id")
    private Long productItemPriceId;
}

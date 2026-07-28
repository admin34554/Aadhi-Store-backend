package com.example.aadhiStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_item_price")
@Getter
@Setter
@NoArgsConstructor
public class ProductItemPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id")
    @JsonIgnore
    private ProductItem productItem;

    @Column(name = "batch_code")
    private String batchCode;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "mrp")
    private Double mrp;

    @Column(name = "msp")
    private Double msp;
}
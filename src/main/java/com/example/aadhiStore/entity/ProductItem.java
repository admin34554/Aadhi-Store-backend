package com.example.aadhiStore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product_item")
@Getter
@Setter
@NoArgsConstructor
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "measure")
    private Long measure;

    @Column(name = "unit")
    private String unit;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_description")
    private String itemDescription;

    @OneToMany(
            mappedBy = "productItem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ProductItemPrice> productItemPrice = new HashSet<>();

    @OneToMany(
            mappedBy = "productItem",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonIgnore
    private List<PurchaseItem> purchaseItems;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_master_new_id")
    @JsonIgnore
    private ProductMasterNew productMasterNew;
}
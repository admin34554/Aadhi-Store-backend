package com.example.aadhiStore.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "product_master_new")
@Getter
@Setter
@NoArgsConstructor
public class ProductMasterNew {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_group_id")
    private Long productGroupId;

    @Column(name = "brand_id")
    private Long brandId;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @Column(name = "hsn_code")
    private String hsnCode;

    @Column(name = "product_code")
    private String productCode;

//    @Column(name = "quantity")
//    private Double quantity;

    @JsonManagedReference
    @OneToMany(mappedBy = "productMasterNew", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductItem> productItems;

    @JsonManagedReference
    @OneToMany(mappedBy = "productMasterNew", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductItemPrice> productItemPrice;
}

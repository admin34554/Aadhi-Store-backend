package com.example.aadhiStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "product_type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductTypeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "type")
    private String productType;

    @Column(name = "code")
    private String code;

    @OneToMany(mappedBy = "productTypeMaster", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductMaster> productMaster;

}

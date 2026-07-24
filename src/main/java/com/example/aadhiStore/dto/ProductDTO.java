package com.example.aadhiStore.dto;

import com.example.aadhiStore.entity.ProductItem;
import com.example.aadhiStore.entity.ProductItemPrice;
import com.example.aadhiStore.entity.TaxMasterNew;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {

    private Long productGroupId;
    private Long brandId;
    private Long supplierId;
    private String productName;
    private String description;
    private String hsnCode;
    private String productCode;
    private List<ProductItem> productItems;
    private List<ProductItemPrice> productItemPrice;
    private TaxMasterNew taxMasterNew;

}

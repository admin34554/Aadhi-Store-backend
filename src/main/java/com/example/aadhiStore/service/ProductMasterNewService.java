package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.ProductDTO;
import com.example.aadhiStore.entity.ProductItemPrice;
import com.example.aadhiStore.entity.ProductMasterNew;
import com.example.aadhiStore.entity.TaxMasterNew;
import com.example.aadhiStore.repository.ProductNewRepository;
import com.example.aadhiStore.repository.TaxMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMasterNewService {

    @Autowired
    private ProductNewRepository productNewRepository;

    @Autowired
    private TaxMasterRepository taxMasterRepository;


    public List<ProductMasterNew> getAllProducts() {
        return productNewRepository.findAll();
    }

    public ProductMasterNew getProductById(Long id) {
        return productNewRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Product not found" +id));
    }

    public ProductMasterNew createProduct(ProductMasterNew productMaster) {
        String code = generateProductCode(productMaster.getProductName());
        productMaster.setProductCode(code);
        return productNewRepository.save(productMaster);
    }

    public List<ProductDTO> searchByCodeOrName(String value) {

        return productNewRepository.searchProducts(value)
                .stream()
                .map(product -> {

                    List<TaxMasterNew> taxes =
                            taxMasterRepository.findByHsnCode(product.getHsnCode());

                    TaxMasterNew tax = taxes.isEmpty() ? null : taxes.get(0);

                    return convertToDTO(product, tax);
                })
                .toList();
    }

    private ProductDTO convertToDTO(ProductMasterNew product, TaxMasterNew tax) {

        ProductDTO dto = new ProductDTO();

        dto.setProductGroupId(product.getProductGroupId());
        dto.setBrandId(product.getBrandId());
        dto.setSupplierId(product.getSupplierId());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setHsnCode(product.getHsnCode());
        dto.setProductCode(product.getProductCode());

        if (product.getProductItems() != null) {

            product.getProductItems().forEach(item -> {

                item.setProductMasterNew(null);

                if (item.getProductItemPrice() != null) {
                    item.getProductItemPrice().forEach(price -> {
                        price.setProductItem(null);
                    });
                }

                item.setPurchaseItems(null);
            });

            dto.setProductItems(product.getProductItems());
        }

        dto.setTaxMasterNew(tax);

        return dto;
    }
    private String generateProductCode(String productName) {

        String prefix = productName.substring(0, 1).toUpperCase();

        String lastCode = productNewRepository.findLastProductCode(prefix);

        int nextNumber = 1;

        if (lastCode != null) {
            String number = lastCode.substring(lastCode.indexOf('-') + 1);
            nextNumber = Integer.parseInt(number) + 1;
        }

        return prefix + "-" + String.format("%03d", nextNumber);
    }

//    @Transactional
//    public void generateMissingCodes() {
//
//        List<ProductMasterNew> products = productNewRepository.findAll();
//
//        for (ProductMasterNew product : products) {
//
//            if (product.getProductCode() == null || product.getProductCode().isBlank()) {
//
//                product.setProductCode(generateProductCode(product.getProductName()));
//            }
//        }
//
//        productNewRepository.saveAll(products);
//    }
}

package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.InvalidInput;
import com.example.aadhiStore.dto.ProductDTO;
import com.example.aadhiStore.entity.*;
import com.example.aadhiStore.repository.ProductNewRepository;
import com.example.aadhiStore.repository.StockMasterRepository;
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

    @Autowired
    private StockMasterRepository stockMasterRepository;


    public List<ProductMasterNew> getAllProducts() {

        List<ProductMasterNew> products = productNewRepository.findAll();

        for (ProductMasterNew product : products) {

            if (product.getProductTypeMasters() != null
                    && !product.getProductTypeMasters().isEmpty()) {

                String productType =
                        product.getProductTypeMasters()
                                .get(0)
                                .getProductType();

                if (productType != null && !productType.isBlank()) {

                    List<TaxMasterNew> taxMasters =
                            taxMasterRepository.findFirstByHsnDescriptionContainingIgnoreCase(productType);

                    if (!taxMasters.isEmpty()) {
                        product.setHsnCode(taxMasters.get(0).getHsnCode());
                    }
                }
            }
        }

        return products;
    }

    public ProductMasterNew getProductById(Long id) {
        return productNewRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Product not found" +id));
    }

    @Transactional
    public ProductMasterNew createProduct(ProductMasterNew productMaster) {

        if (productMaster.getProductItems() != null) {

            for (ProductItem productItem : productMaster.getProductItems()) {
                productItem.setProductMasterNew(productMaster);
            }
        }

        if (productMaster.getProductType() != null &&
                !productMaster.getProductType().trim().isEmpty()) {

            ProductTypeMaster productTypeMaster = new ProductTypeMaster();

            productTypeMaster.setProductType(productMaster.getProductType());
            productTypeMaster.setCode(productMaster.getProductCode());


            productTypeMaster.setProductMasterNew(productMaster);

            productMaster.getProductTypeMasters().add(productTypeMaster);
        }

        return productNewRepository.save(productMaster);
    }

    public List<ProductDTO> searchByCodeOrName(String value) {

        return productNewRepository.searchProducts(value)
                .stream()
                .map(product -> {

                    List<TaxMasterNew> taxes = taxMasterRepository.findByHsnCode(product.getHsnCode());

                    TaxMasterNew tax = taxes.isEmpty() ? null : taxes.get(0);

                    return convertToDTO(product);
                })
                .toList();
    }

    private ProductDTO convertToDTO(ProductMasterNew product) {

        ProductDTO dto = new ProductDTO();

        dto.setProductGroupId(product.getProductGroupId());
        dto.setBrandId(product.getBrandId());
        dto.setSupplierId(product.getSupplierId());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setProductCode(product.getProductCode());

        String hsnCode = product.getHsnCode();

        if (product.getProductItems() != null &&
                !product.getProductItems().isEmpty()) {

            ProductItem item = product.getProductItems().get(0);

            List<StockMaster> stocks =
                    stockMasterRepository
                            .findByItemNameIgnoreCase(item.getItemName());

            if (stocks != null && !stocks.isEmpty()) {

                String stockHsn = stocks.get(0).getHsnCode();

                if (stockHsn != null && !stockHsn.trim().isEmpty()) {
                    hsnCode = stockHsn.trim();
                }
            }
        }

        dto.setHsnCode(hsnCode);

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


        if (hsnCode != null && !hsnCode.trim().isEmpty()) {

            System.out.println("Searching tax for HSN: [" + hsnCode + "]");

            List<TaxMasterNew> taxes =
                    taxMasterRepository.findByHsnCode(hsnCode.trim());

            System.out.println("Tax records found: " + taxes.size());

            if (!taxes.isEmpty()) {
                dto.setTaxMasterNew(taxes.get(0));
            }
        }

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
    @Transactional
    public ProductMasterNew updateProduct(Long id, ProductMasterNew productMaster) {

        ProductMasterNew existingProduct =
                productNewRepository.findById(id)
                        .orElseThrow(() ->
                                new InvalidInput("Product not found for this id: " + id));

        // Update product fields
        existingProduct.setProductGroupId(productMaster.getProductGroupId());
        existingProduct.setBrandId(productMaster.getBrandId());
        existingProduct.setSupplierId(productMaster.getSupplierId());
        existingProduct.setProductName(productMaster.getProductName());
        existingProduct.setDescription(productMaster.getDescription());
        existingProduct.setHsnCode(productMaster.getHsnCode());
        existingProduct.setProductCode(productMaster.getProductCode());

        // Remove old product items
        existingProduct.getProductItems().clear();

        // Add new product items
        if (productMaster.getProductItems() != null) {

            for (ProductItem item : productMaster.getProductItems()) {

                // VERY IMPORTANT
                item.setProductMasterNew(existingProduct);

                existingProduct.getProductItems().add(item);
            }
        }
        if (productMaster.getProductTypeMasters() != null) {

            existingProduct.getProductTypeMasters().clear();

            ProductTypeMaster productTypeMaster = new ProductTypeMaster();

            productTypeMaster.setProductType(productMaster.getProductType());
            productTypeMaster.setCode(productMaster.getProductCode());

            productTypeMaster.setProductMasterNew(existingProduct);

            existingProduct.getProductTypeMasters().add(productTypeMaster);
        }

        return productNewRepository.save(existingProduct);
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

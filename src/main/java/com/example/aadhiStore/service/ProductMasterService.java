package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.InvalidInput;
import com.example.aadhiStore.entity.ProductMaster;
import com.example.aadhiStore.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductMasterService {

    @Autowired
    private final ProductRepository productRepository;

    public ProductMasterService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductMaster> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductMaster getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Product not found" +id));
    }

    public ProductMaster createProduct(ProductMaster productMaster) {
        return productRepository.save(productMaster);
    }

    public ProductMaster updateProduct(Long id, ProductMaster productMaster) {
        if (productRepository.existsById(id)) {
            productMaster.setId(id);
            return productRepository.save(productMaster);
        }
        else {
            throw new InvalidInput("Product not found for this id" +id);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}

package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.InvalidInput;
import com.example.aadhiStore.entity.ProductMaster;
import com.example.aadhiStore.entity.ProductTypeMaster;
import com.example.aadhiStore.repository.ProductTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    public List<ProductTypeMaster> getAllProductTypes() {
        return productTypeRepository.findAll();
    }

    public ProductTypeMaster getProductType(Long id) {
        return productTypeRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Product Type not found" +id));
    }

    public ProductTypeMaster createProductType(ProductTypeMaster productTypeMaster) {
        return productTypeRepository.save(productTypeMaster);
    }

    public ProductTypeMaster updateProductType(Long id, ProductTypeMaster productTypeMaster) {
        if (productTypeRepository.existsById(id)) {
            productTypeMaster.setId(id);
            return productTypeRepository.save(productTypeMaster);
        }
        else {
            throw new InvalidInput("Product not found for this id" +id);
        }
    }

    public void deleteProductType(Long id) {
        productTypeRepository.deleteById(id);
    }

    public List<ProductTypeMaster> getAllProductTypesByFilter(String name) {

        if (name == null) {
            return productTypeRepository.findAll();
        }
        return productTypeRepository.findByProductTypeContainingIgnoreCaseOrCodeContainingIgnoreCase(name, name);
    }
}

package com.example.aadhiStore.controller;


import com.example.aadhiStore.entity.ProductMaster;
import com.example.aadhiStore.entity.ProductTypeMaster;
import com.example.aadhiStore.service.ProductMasterService;
import com.example.aadhiStore.service.ProductTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://aathithanstore.netlify.app"
})
@RequestMapping("/api/v1/product-type")
public class ProductTypeController {


    private static final Logger log = LoggerFactory.getLogger(ProductMasterController.class);
    @Autowired
    private ProductTypeService productTypeService;

    @GetMapping(value = "/list-view")
    private List<ProductTypeMaster> getAllProducts(@RequestParam(required = false) String name) {
        log.info("Fetched all entries successfully");
        return productTypeService.getAllProductTypesByFilter(name);
    }

    @GetMapping(value = "/{id}")
    private ProductTypeMaster getProductById(@PathVariable Long id) {
        return productTypeService.getProductType(id);
    }

    @PostMapping
    private ResponseEntity<ProductTypeMaster> createProduct(@RequestBody ProductTypeMaster productMaster) {
        ProductTypeMaster createProduct = productTypeService.createProductType(productMaster);
        log.info("Entry created successfully" + productMaster);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    private ResponseEntity<ProductTypeMaster> updateProduct(@PathVariable Long id, @RequestBody ProductTypeMaster productMaster) {
        ProductTypeMaster updateProduct = productTypeService.updateProductType(id, productMaster);
        if (productMaster != null) {
            log.info("Entry updated successfully" + productMaster);
            return new ResponseEntity<>(updateProduct, HttpStatus.OK);
        }
        else {
            log.info("Entry not found" + productMaster);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    private ResponseEntity<Void> deleteProductType(@PathVariable Long id) {
        productTypeService.deleteProductType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

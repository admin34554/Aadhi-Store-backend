package com.example.aadhiStore.controller;


import com.example.aadhiStore.dto.ProductDTO;
import com.example.aadhiStore.entity.ProductMaster;
import com.example.aadhiStore.entity.ProductMasterNew;
import com.example.aadhiStore.service.ProductMasterNewService;
import com.example.aadhiStore.service.ProductMasterService;
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
@RequestMapping("api/v1/product-master")
public class ProductMasterController {


    private static final Logger log = LoggerFactory.getLogger(ProductMasterController.class);
    @Autowired
    private final ProductMasterService productMasterService;

    @Autowired
    private ProductMasterNewService productMasterNewService;

    public ProductMasterController(ProductMasterService productMasterService) {
        this.productMasterService = productMasterService;
    }

    @GetMapping(value = "/list-view")
    private List<ProductMaster> getAllProducts() {
        log.info("Fetched all entries successfully");
        return productMasterService.getAllProducts();
    }

    @GetMapping(value = "/{id}")
    private ProductMaster getProductById(@PathVariable Long id) {
        return productMasterService.getProductById(id);
    }

    @PostMapping
    private ResponseEntity<ProductMaster> createProduct(@RequestBody ProductMaster productMaster) {
        ProductMaster createProduct = productMasterService.createProduct(productMaster);
        log.info("Entry created successfully" + productMaster);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    private ResponseEntity<ProductMaster> updateProduct(@PathVariable Long id, @RequestBody ProductMaster productMaster) {
        ProductMaster updateProduct = productMasterService.updateProduct(id, productMaster);
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
    private ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productMasterService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/product-new/search")
    private List<ProductDTO> getBySearch(@RequestParam String code) {
        return productMasterNewService.searchByCodeOrName(code);
    }

    //New controller
    @GetMapping("/product-new/list-view")
    public List<ProductMasterNew> getAllProductsNew() {
        log.info("Fetched all entries successfully");
        return productMasterNewService.getAllProducts();
    }

    @GetMapping("/product-new/{id}")
    private ProductMasterNew getNewProductById(@PathVariable Long id) {
        return productMasterNewService.getProductById(id);
    }

    @PostMapping("/product-new/create")
    private ResponseEntity<ProductMasterNew> createNewProduct(@RequestBody ProductMasterNew productMaster) {
        ProductMasterNew createProduct = productMasterNewService.createProduct(productMaster);
        log.info("Entry created successfully" + productMaster);
        return new ResponseEntity<>(createProduct, HttpStatus.CREATED);
    }

//    @PostMapping("/generate-product-codes")
//    public ResponseEntity<String> generateProductCodes() {
//
//        productMasterNewService.generateMissingCodes();
//
//        return ResponseEntity.ok("Product codes generated successfully.");
//    }
}

package com.example.aadhiStore.controller;


import com.example.aadhiStore.entity.SupplierMaster;
import com.example.aadhiStore.service.SupplierMasterService;
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
@RequestMapping("/api/v1/supplier-master")
public class SupplierMasterController {

    private static final Logger log = LoggerFactory.getLogger(SupplierMasterController.class);
    @Autowired
    private SupplierMasterService supplierMasterService;


    @GetMapping("/list-view")
    private List<SupplierMaster> getAllSupplier() {
        return supplierMasterService.getAllSupplier();
    }

    @GetMapping("/{id}")
    private SupplierMaster getById(@PathVariable Long id) {
        return supplierMasterService.getById(id);
    }

    @PostMapping
    private ResponseEntity<SupplierMaster> createSupplier(@RequestBody SupplierMaster supplierMaster) {
        SupplierMaster createSupplier = supplierMasterService.createSupplier(supplierMaster);
        log.info("Entry created successfully" + supplierMaster);
        return new ResponseEntity<>(createSupplier, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<SupplierMaster> updateSupplier(@PathVariable Long id, @RequestBody SupplierMaster supplierMaster) {
        SupplierMaster updatedSupplier = supplierMasterService.updateSupplier(id, supplierMaster);
        if (updatedSupplier != null) {
            log.info("Entry updated successfully" + supplierMaster);
            return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
        }
        else {
            log.info("Entry not found" + supplierMaster);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
        supplierMasterService.deleteSupplier(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

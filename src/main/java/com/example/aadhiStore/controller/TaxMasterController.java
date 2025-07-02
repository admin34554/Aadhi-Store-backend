package com.example.aadhiStore.controller;


import com.example.aadhiStore.entity.TaxMaster;
import com.example.aadhiStore.service.TaxMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/tax-master")
public class TaxMasterController {

    private static final Logger log = LoggerFactory.getLogger(TaxMasterController.class);
    @Autowired
    private final TaxMasterService taxMasterService;

    public TaxMasterController(TaxMasterService taxMasterService) {
        this.taxMasterService = taxMasterService;
    }

    @GetMapping(value = "/list-view")
    private List<TaxMaster> getAllTaxes() {
        log.info("Fetched all entries successfully");
        return taxMasterService.getAllTaxes();
    }

    @GetMapping(value = "/{id}")
    private TaxMaster getTaxMasterById(@PathVariable Long id) {
        return taxMasterService.getTaxById(id);
    }

    @PostMapping
    private ResponseEntity<TaxMaster> createTax(@RequestBody TaxMaster taxMaster) {
        TaxMaster createTax = taxMasterService.createTax(taxMaster);
        log.info("Entry created successfully" + taxMaster);
        return new ResponseEntity<>(createTax, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    private ResponseEntity<TaxMaster> updateTax(@PathVariable Long id, @RequestBody TaxMaster taxMaster) {
        TaxMaster updatedTax = taxMasterService.updateTax(id, taxMaster);
        if (taxMaster != null) {
            log.info("Entry updated successfully" + taxMaster);
            return new ResponseEntity<>(updatedTax, HttpStatus.OK);
        }
        else {
            log.info("Entry not found" + taxMaster);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    private ResponseEntity<Void> deleteTax(@PathVariable Long id) {
        taxMasterService.deleteTax(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

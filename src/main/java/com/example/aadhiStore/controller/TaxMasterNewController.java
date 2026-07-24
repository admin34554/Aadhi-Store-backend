package com.example.aadhiStore.controller;


import com.example.aadhiStore.entity.TaxMasterNew;
import com.example.aadhiStore.repository.TaxMasterRepository;
import com.example.aadhiStore.service.TaxMasterNewService;
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
@RequestMapping("/api/v1/tax-new-controller")
public class TaxMasterNewController {

    private static final Logger log = LoggerFactory.getLogger(TaxMasterController.class);


    @Autowired
    private TaxMasterRepository taxMasterRepository;

    @Autowired
    private TaxMasterNewService taxMasterNewService;


    @GetMapping(value = "/list-view")
    private List<TaxMasterNew> getAllTaxes(@RequestParam(required = false) String name) {
        log.info("Fetched all entries successfully");
        return taxMasterNewService.getAllTaxes();
    }

    @GetMapping(value = "/{id}")
    private TaxMasterNew getTaxMasterById(@PathVariable Long id) {
        return taxMasterNewService.getTaxById(id);
    }

    @PostMapping
    private ResponseEntity<TaxMasterNew> createTax(@RequestBody TaxMasterNew taxMaster) {
        TaxMasterNew createTax = taxMasterNewService.createTax(taxMaster);
        log.info("Entry created successfully" + taxMaster);
        return new ResponseEntity<>(createTax, HttpStatus.CREATED);
    }


}

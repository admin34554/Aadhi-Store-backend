package com.example.aadhiStore.controller;

import com.example.aadhiStore.dto.LorryDTO;
import com.example.aadhiStore.entity.LorryMaster;
import com.example.aadhiStore.entity.SupplierPayment;
import com.example.aadhiStore.service.SupplierPaymentService;
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
@RequestMapping("/api/v1/supplier-payment")
public class SupplierPaymentController {

    @Autowired
    private SupplierPaymentService supplierPaymentService;

    private static final Logger log = LoggerFactory.getLogger(SupplierPaymentController.class);


    @GetMapping("/list-view")
    private List<SupplierPayment> getAllSupplierPayment() {
        log.info("Fetched all entries successfully");
        return supplierPaymentService.getAllSupplierPayments();
    }

    @GetMapping("/{id}")
    private SupplierPayment getSupplierPaymentById(@PathVariable Long id) {
        return supplierPaymentService.getById(id);
    }

    @PostMapping
    private ResponseEntity<SupplierPayment> createSupplierPayment(@RequestBody SupplierPayment supplierPayment) {
        SupplierPayment createPayment = supplierPaymentService.createSupplierPayment(supplierPayment);
        log.info("Entry created successfully" + supplierPayment);
        return new ResponseEntity<>(createPayment, HttpStatus.CREATED);
    }
}

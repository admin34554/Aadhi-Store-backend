package com.example.aadhiStore.controller;

import com.example.aadhiStore.entity.PurchaseBill;
import com.example.aadhiStore.service.PurchaseBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/purchase-bill")
public class PurchaseBillController {

    @Autowired
    private PurchaseBillService purchaseBillService;

    @GetMapping("/list-view")
    public List<PurchaseBill> getAllPurchase() {
        return purchaseBillService.getAllPurchaseBill();
    }

    @GetMapping("/{id}")
    public PurchaseBill getById(@PathVariable Long id) {
        return purchaseBillService.getPurchaseBillById(id);
    }

    @PostMapping
    private ResponseEntity<PurchaseBill> createPurchase(@RequestBody PurchaseBill purchaseBill) {
        PurchaseBill newPurchase = purchaseBillService.createPurchaseBill(purchaseBill);
        return new ResponseEntity<>(newPurchase, HttpStatus.CREATED);
    }
}

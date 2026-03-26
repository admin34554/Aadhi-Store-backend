package com.example.aadhiStore.controller;


import com.example.aadhiStore.entity.CreditBill;
import com.example.aadhiStore.service.CreditBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/credit-bill")
public class CreditBillController {

    @Autowired
    private CreditBillService creditBillService;

    @GetMapping("/list-view")
    public List<CreditBill> getAllCreditBills() {
        return creditBillService.getAllCrediBills();
    }

    @GetMapping("/{id}")
    private CreditBill getCreditBillById(@PathVariable Long id) {
        return creditBillService.getCreditBillsById(id);
    }

    @PostMapping
    private ResponseEntity<CreditBill> createCreditBills(@RequestBody CreditBill creditBill) {
        CreditBill createCredit = creditBillService.createCreditBill(creditBill);
        return new ResponseEntity<>(createCredit, HttpStatus.CREATED);
    }
}

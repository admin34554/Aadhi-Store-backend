package com.example.aadhiStore.controller;


import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.service.CashBillService;
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
@RequestMapping("/api/v1/cash-bill")
public class CashBillController {

    @Autowired
    private CashBillService cashBillService;

    @GetMapping("/list-view")
    public List<CashBill> getAllCashBills() {
        return cashBillService.getAllCashBills();
    }

    @GetMapping("/{id}")
    private CashBill getCashBillById(@PathVariable Long id) {
        return cashBillService.getCashBillsById(id);
    }

    @PostMapping
    private ResponseEntity<CashBill> createCashBills(@RequestBody CashBill cashBill) {
        CashBill createCash = cashBillService.createCashBill(cashBill);
        return new ResponseEntity<>(createCash, HttpStatus.CREATED);
    }


}

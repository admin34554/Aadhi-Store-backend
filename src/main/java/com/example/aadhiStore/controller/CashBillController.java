package com.example.aadhiStore.controller;


import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.service.CashBillService;
import com.example.aadhiStore.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
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

    @Autowired
    private PdfService pdfService;

    @GetMapping("/list-view")
    public List<CashBill> getAllCashBills() {
        return cashBillService.getAllCashBills();
    }

    @GetMapping("/cb/{id}")
    private CashBill getCashBillById(@PathVariable Long id) {
        return cashBillService.getCashBillsById(id);
    }

    @GetMapping("/bill-no/{billNo}")
    public CashBill getCashBillByBillNo(@PathVariable String billNo) {
        return cashBillService.getCashBillByBillNo(billNo);
    }
    
    @PostMapping
    private ResponseEntity<CashBill> createCashBills(@RequestBody CashBill cashBill) {
        CashBill createCash = cashBillService.createCashBill(cashBill);
        return new ResponseEntity<>(createCash, HttpStatus.CREATED);
    }

    @GetMapping("/pdf/{billNo}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable String billNo) {

        CashBill cashBill = cashBillService.getCashBillByBillNo(billNo);
        ByteArrayInputStream bis = pdfService.generateCashBillPdf(cashBill);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition",
                "attachment; filename=cashBill.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

}

package com.example.aadhiStore.controller;


import com.example.aadhiStore.entity.CreditBill;
import com.example.aadhiStore.service.CreditBillService;
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
@RequestMapping("/api/v1/credit-bill")
public class CreditBillController {

    @Autowired
    private CreditBillService creditBillService;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/list-view")
    public List<CreditBill> getAllCreditBills() {
        return creditBillService.getAllCrediBills();
    }

    @GetMapping("/{id}")
    private CreditBill getCreditBillById(@PathVariable Long id) {
        return creditBillService.getCreditBillsById(id);
    }

    @GetMapping("/bill-no/{billNo}")
    public CreditBill getCreditBillByBillNo(@PathVariable String billNo) {
        return creditBillService.getCreditBillByBillNo(billNo);
    }

    @PostMapping
    private ResponseEntity<CreditBill> createCreditBills(@RequestBody CreditBill creditBill) {
        CreditBill createCredit = creditBillService.createCreditBill(creditBill);
        return new ResponseEntity<>(createCredit, HttpStatus.CREATED);
    }

    @GetMapping("/pdf/{billNo}")
    public ResponseEntity<byte[]> generatePdf(@PathVariable String billNo) {

        CreditBill cashBill = creditBillService.getCreditBillByBillNo(billNo);
        ByteArrayInputStream bis = pdfService.generateCreditBillPdf(cashBill);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition",
                "attachment; filename=creditBill.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }
}

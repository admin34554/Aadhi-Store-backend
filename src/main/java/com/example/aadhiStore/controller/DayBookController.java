package com.example.aadhiStore.controller;


import com.example.aadhiStore.dto.DayBookDTO;
import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.service.DayBookService;
import com.example.aadhiStore.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://aathithanstore.netlify.app"
})
@RequestMapping("/api/v1/day-book")
public class DayBookController {

    @Autowired
    private DayBookService dayBookService;

    @Autowired
    private PdfService pdfService;

    @GetMapping("/list-view")
    public DayBookDTO getDayBook(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return dayBookService.getDayBook(fromDate, toDate);
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {

        DayBookDTO dayBookDTO = dayBookService.getDayBook(fromDate, toDate);
        ByteArrayInputStream bis = pdfService.generateDayBook(dayBookDTO);

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition",
                "attachment; filename=DayBook.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }
}

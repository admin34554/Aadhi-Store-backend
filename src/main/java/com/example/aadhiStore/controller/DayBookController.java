package com.example.aadhiStore.controller;


import com.example.aadhiStore.dto.DayBookDTO;
import com.example.aadhiStore.service.DayBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list-view")
    public DayBookDTO getDayBook(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) {
        return dayBookService.getDayBook(fromDate, toDate);
    }
}

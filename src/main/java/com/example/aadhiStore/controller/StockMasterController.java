package com.example.aadhiStore.controller;

import com.example.aadhiStore.entity.StockMaster;
import com.example.aadhiStore.repository.StockMasterRepository;
import com.example.aadhiStore.service.StockMasterService;
import jakarta.persistence.OrderBy;
import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/stock-master")
public class StockMasterController {

    @Autowired
    private StockMasterRepository stockMasterRepository;

    @Autowired
    private StockMasterService stockMasterService;

    @GetMapping("/list-view")
    public List<StockMaster> getStockMasterList() {
        return stockMasterRepository.findAll();
    }

    @PostMapping
    private StockMaster save(@RequestBody StockMaster stockMaster) {
        return stockMasterService.saveStock(stockMaster);
    }

    @PutMapping("/{id}")
    private StockMaster update(@PathVariable Long id, @RequestBody StockMaster stockMaster) {
        return stockMasterService.saveStock(stockMaster);
    }

    @GetMapping("/{id}")
    private StockMaster getById(@PathVariable Long id) {
        return stockMasterRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Stock not found: " + id));
    }
}

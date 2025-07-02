package com.example.aadhiStore.controller;

import com.example.aadhiStore.entity.LorryMaster;
import com.example.aadhiStore.service.LorryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lorry-master")
public class LorryMasterController {

    private static final Logger log = LoggerFactory.getLogger(LorryMasterController.class);
    @Autowired
    private final LorryService lorryService;

    public LorryMasterController(LorryService lorryService) {
        this.lorryService = lorryService;
    }

    @GetMapping("/list-view")
    private List<LorryMaster> getAllLorry() {
        log.info("Fetched all entries successfully");
        return lorryService.getAllLorry();
    }

    @GetMapping("/{id}")
    private LorryMaster getLorryById(@PathVariable Long id) {
        return lorryService.getLorryById(id);
    }

    @PostMapping
    private ResponseEntity<LorryMaster> createLorry(@RequestBody LorryMaster lorryMaster) {
        LorryMaster createLorry = lorryService.createLorry(lorryMaster);
        log.info("Entry created successfully" + lorryMaster);
        return new ResponseEntity<>(createLorry, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<LorryMaster> updateLorry(@PathVariable Long id, @RequestBody LorryMaster lorryMaster) {
        LorryMaster updatedLorry = lorryService.updateLorry(id, lorryMaster);
        if (updatedLorry != null) {
            log.info("Entry updated successfully" + lorryMaster);
            return new ResponseEntity<>(updatedLorry, HttpStatus.OK);
        }
        else {
            log.info("Entry not found" + lorryMaster);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteLorry(@PathVariable Long id) {
        lorryService.deleteLorry(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

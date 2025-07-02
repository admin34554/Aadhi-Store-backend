package com.example.aadhiStore.controller;

import com.example.aadhiStore.entity.BrokerMaster;
import com.example.aadhiStore.service.BrokerMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/broker-master")
public class BrokerMasterController {

    private static final Logger log = LoggerFactory.getLogger(BrokerMasterController.class);
    @Autowired
    private final BrokerMasterService brokerMasterService;

    public BrokerMasterController(BrokerMasterService brokerMasterService) {
        this.brokerMasterService = brokerMasterService;
    }


    @GetMapping("/list-view")
    private List<BrokerMaster> getAllBrokers() {
        log.info("Fetched all entries successfully");
        return brokerMasterService.getAllBrokers();
    }

    @GetMapping("/{id}")
    private BrokerMaster getBrokerById(@PathVariable Long id) {
        return brokerMasterService.getBrokerById(id);
    }

    @PostMapping
    private ResponseEntity<BrokerMaster> createBroker(@RequestBody BrokerMaster brokerMaster) {
        BrokerMaster createBrokerr = brokerMasterService.createBroker(brokerMaster);
        log.info("Entry created successfully" + brokerMaster);
        return new ResponseEntity<>(createBrokerr, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<BrokerMaster> updateBroker(@PathVariable Long id, @RequestBody BrokerMaster brokerMaster) {
        BrokerMaster updatedBroker = brokerMasterService.updateBroker(id, brokerMaster);
        if (updatedBroker != null) {
            log.info("Entry updated successfully" + brokerMaster);
            return new ResponseEntity<>(updatedBroker, HttpStatus.OK);
        }
        else {
            log.info("Entry not found" + brokerMaster);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteBroker(@PathVariable Long id) {
        brokerMasterService.deleteBroker(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



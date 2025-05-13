package com.example.aadhiStore.controller;

import com.example.aadhiStore.entity.BrokerMaster;
import com.example.aadhiStore.service.BrokerMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/broker-master")
public class BrokerMasterController {

    @Autowired
    private final BrokerMasterService brokerMasterService;

    public BrokerMasterController(BrokerMasterService brokerMasterService) {
        this.brokerMasterService = brokerMasterService;
    }


    @GetMapping("/list-view")
    private List<BrokerMaster> getAllBrokers() {
        return brokerMasterService.getAllBrokers();
    }

    @GetMapping("/{id}")
    private BrokerMaster getBrokerById(@PathVariable Long id) {
        return brokerMasterService.getBrokerById(id);
    }

    @PostMapping
    private ResponseEntity<BrokerMaster> createBroker(@RequestBody BrokerMaster brokerMaster) {
        BrokerMaster createBrokerr = brokerMasterService.createBroker(brokerMaster);
        return new ResponseEntity<>(createBrokerr, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<BrokerMaster> updateBroker(@PathVariable Long id, @RequestBody BrokerMaster brokerMaster) {
        BrokerMaster updatedBroker = brokerMasterService.updateBroker(id, brokerMaster);
        if (updatedBroker != null) {
            return new ResponseEntity<>(updatedBroker, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteBroker(@PathVariable Long id) {
        brokerMasterService.deleteBroker(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



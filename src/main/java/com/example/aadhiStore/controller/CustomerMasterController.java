package com.example.aadhiStore.controller;

import com.example.aadhiStore.entity.CustomerMaster;
import com.example.aadhiStore.service.CustomerMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer-master")
public class CustomerMasterController {

    @Autowired
    private final CustomerMasterService customerMasterService;

    public CustomerMasterController(CustomerMasterService customerMasterService) {
        this.customerMasterService = customerMasterService;
    }


    @GetMapping("/list-view")
    private List<CustomerMaster> getAllCustomers() {
        return customerMasterService.getAllCustomers();
    }

    @GetMapping("/{id}")
    private CustomerMaster getCustomerById(@PathVariable Long id) {
            return customerMasterService.getCustomerById(id);
    }

    @PostMapping
    private ResponseEntity<CustomerMaster> createCustomer(@RequestBody CustomerMaster customerMaster) {
        CustomerMaster createCustomer = customerMasterService.createCustomer(customerMaster);
        return new ResponseEntity<>(createCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    private ResponseEntity<CustomerMaster> updateCustomer(@PathVariable Long id, @RequestBody CustomerMaster customerMaster) {
            CustomerMaster updatedCustomer = customerMasterService.updateCustomer(id, customerMaster);
            if (updatedCustomer != null) {
                return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
            }
            else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
            customerMasterService.deleteCustomer(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

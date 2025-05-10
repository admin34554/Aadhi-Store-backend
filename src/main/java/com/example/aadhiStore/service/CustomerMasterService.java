package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.InvalidInput;
import com.example.aadhiStore.entity.CustomerMaster;
import com.example.aadhiStore.repository.CustomerMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerMasterService {

    @Autowired
    private final CustomerMasterRepository customerMasterRepository;

    public CustomerMasterService(CustomerMasterRepository customerMasterRepository) {
        this.customerMasterRepository = customerMasterRepository;
    }

    public List<CustomerMaster> getAllCustomers() {
        return customerMasterRepository.findAll();
    }

    public CustomerMaster getCustomerById(Long id) {
        return customerMasterRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Customer not found for this id" + id));
    }

    public CustomerMaster createCustomer(CustomerMaster customerMaster) {
        return customerMasterRepository.save(customerMaster);
    }

    public CustomerMaster updateCustomer(Long id, CustomerMaster customerMaster) {
            if (customerMasterRepository.existsById(id)) {
                customerMaster.setId(id);
                return customerMasterRepository.save(customerMaster);
            } else {
                throw new InvalidInput("Customer not found for this id" +id);
            }
    }

    public void deleteCustomer(Long id) {
        customerMasterRepository.deleteById(id);
    }

}

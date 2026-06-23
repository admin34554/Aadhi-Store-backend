package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.SupplierPayment;
import com.example.aadhiStore.repository.SupplierPaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierPaymentService {

    @Autowired
    private SupplierPaymentRepository supplierPaymentRepository;

    public List<SupplierPayment> getAllSupplierPayments() {
        return supplierPaymentRepository.findAll();
    }

    public SupplierPayment getById(Long id) {
        return supplierPaymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier not found for this id" + id));
    }

    public SupplierPayment createSupplierPayment(SupplierPayment supplierPayment) {
        return supplierPaymentRepository.save(supplierPayment);
    }
}

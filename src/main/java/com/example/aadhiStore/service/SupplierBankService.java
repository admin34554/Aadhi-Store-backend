package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.InvalidInput;
import com.example.aadhiStore.entity.SupplierBankDetails;
import com.example.aadhiStore.repository.SupplierBankRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierBankService {

    @Autowired
    private SupplierBankRepository supplierBankRepository;


    public List<SupplierBankDetails> getAllSupplierBankDetails() {
        return supplierBankRepository.findAll();
    }

    public SupplierBankDetails getById(Long id) {
        return supplierBankRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier not found for this id" + id));
    }

    public SupplierBankDetails createSupplierBankDetails(SupplierBankDetails supplierBankDetails) {
        return supplierBankRepository.save(supplierBankDetails);
    }

    public SupplierBankDetails updateSupplierBankDetails(Long id, SupplierBankDetails supplierBankDetails) {
        if (supplierBankRepository.existsById(id)) {
            supplierBankDetails.setId(id);
            return supplierBankRepository.save(supplierBankDetails);
        } else {
            throw new InvalidInput("Supplier not found for this id" +id);
        }
    }

    public void deleteSupplierBankDetails(Long id) {
        supplierBankRepository.deleteById(id);
    }

}

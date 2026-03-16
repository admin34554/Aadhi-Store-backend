package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.InvalidInput;
import com.example.aadhiStore.entity.SupplierMaster;
import com.example.aadhiStore.repository.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierMasterService {

    @Autowired
    private final SupplierRepository supplierRepository;

    public SupplierMasterService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierMaster> getAllSupplier() {
        return supplierRepository.findAll();
    }

    public SupplierMaster getById(Long id) {
        return supplierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier not found for this id" + id));
    }

    public SupplierMaster createSupplier(SupplierMaster supplierMaster) {
        return supplierRepository.save(supplierMaster);
    }

    public SupplierMaster updateSupplier(Long id, SupplierMaster supplierMaster) {
        if (supplierRepository.existsById(id)) {
            supplierMaster.setId(id);
            return supplierRepository.save(supplierMaster);
        } else {
            throw new InvalidInput("Supplier not found for this id" +id);
        }
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }

}

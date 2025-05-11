package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.InvalidInput;
import com.example.aadhiStore.entity.TaxMaster;
import com.example.aadhiStore.repository.TaxRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxMasterService {

    @Autowired
    private final TaxRepository taxRepository;

    public TaxMasterService(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }

    public List<TaxMaster> getAllTaxes() {
        return taxRepository.findAll();
    }

    public TaxMaster getTaxById(Long id) {
        return taxRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Tax not found" +id));
    }

    public TaxMaster createTax(TaxMaster taxMaster) {
        return taxRepository.save(taxMaster);
    }

    public TaxMaster updateTax(Long id, TaxMaster taxMaster) {
        if (taxRepository.existsById(id)) {
            taxMaster.setId(id);
            return taxRepository.save(taxMaster);
        }
        else {
            throw new InvalidInput("Tax not found for this id" +id);
        }
    }

    public void deleteTax(Long id) {
        taxRepository.deleteById(id);
    }
}

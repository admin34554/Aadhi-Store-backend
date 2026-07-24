package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.InvalidInput;
import com.example.aadhiStore.entity.TaxMasterNew;
import com.example.aadhiStore.repository.TaxMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxMasterNewService {

    @Autowired
    private TaxMasterRepository taxMasterRepository;

    public List<TaxMasterNew> getAllTaxes() {
        return taxMasterRepository.findAll();
    }

    public TaxMasterNew getTaxById(Long id) {
        return taxMasterRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Tax not found" +id));
    }

    public TaxMasterNew createTax(TaxMasterNew taxMaster) {
        return taxMasterRepository.save(taxMaster);
    }

    public TaxMasterNew updateTax(Long id, TaxMasterNew taxMaster) {
        if (taxMasterRepository.existsById(id)) {
            taxMaster.setId(id);
            return taxMasterRepository.save(taxMaster);
        }
        else {
            throw new InvalidInput("Tax not found for this id" +id);
        }
    }

    public void deleteTax(Long id) {
        taxMasterRepository.deleteById(id);
    }
}

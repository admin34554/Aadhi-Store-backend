package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.InvalidInput;
import com.example.aadhiStore.entity.LorryMaster;
import com.example.aadhiStore.repository.LorryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LorryService {

    @Autowired
    private final LorryRepository lorryRepository;

    public LorryService(LorryRepository lorryRepository) {
        this.lorryRepository = lorryRepository;
    }

    public List<LorryMaster> getAllLorry() {
        return lorryRepository.findAll();
    }

    public LorryMaster getLorryById(Long id) {
        return lorryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Lorry not found for this id" + id));
    }

    public LorryMaster createLorry(LorryMaster lorryMaster) {
        return lorryRepository.save(lorryMaster);
    }

    public LorryMaster updateLorry(Long id, LorryMaster lorryMaster) {
        if (lorryRepository.existsById(id)) {
            lorryMaster.setId(id);
            return lorryRepository.save(lorryMaster);
        } else {
            throw new InvalidInput("Lorry not found for this id" +id);
        }
    }

    public void deleteLorry(Long id) {
        lorryRepository.deleteById(id);
    }

}

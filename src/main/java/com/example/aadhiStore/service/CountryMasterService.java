package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.CountryMaster;
import com.example.aadhiStore.repository.CountryMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryMasterService {

    @Autowired
    private CountryMasterRepository countryMasterRepository;

    public CountryMasterService(CountryMasterRepository countryMasterRepository){
        this.countryMasterRepository = countryMasterRepository;
    }

    public List<CountryMaster> getAllCountry(){
        return countryMasterRepository.findAll();
    }

    public CountryMaster getCountryById(Long id){
        return countryMasterRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Entity not found" + id));
    }

    public CountryMaster createCountry(CountryMaster countryMaster){
        return countryMasterRepository.save(countryMaster);
    }
}


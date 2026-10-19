package com.example.aadhiStore.controller;

import com.example.aadhiStore.entity.CountryMaster;
import com.example.aadhiStore.service.CountryMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/country-master")
public class CountryMasterController {

    public static final Logger log = LoggerFactory.getLogger(CountryMasterController.class);

    @Autowired
    private CountryMasterService countryMasterService;

    public CountryMasterController(CountryMasterService countryMasterService){
        this.countryMasterService = countryMasterService;
    }

    @GetMapping("/list-view")
    private List<CountryMaster> getAllCountry(){
        log.info("fetched all entries");
        return countryMasterService.getAllCountry();
    }

    @GetMapping("/{id}")
    private CountryMaster getCountryById(@PathVariable Long id){
        return countryMasterService.getCountryById(id);
    }

    @PostMapping
    private ResponseEntity<CountryMaster> createCountry(@RequestBody CountryMaster countryMaster){
        CountryMaster createCountry = countryMasterService.createCountry(countryMaster);
        log.info("country created succesfully" + countryMaster);
        return new ResponseEntity<>(countryMaster, HttpStatus.CREATED);
    }
}


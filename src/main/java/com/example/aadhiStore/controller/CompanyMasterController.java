package com.example.aadhiStore.controller;


import com.example.aadhiStore.entity.CompanyMaster;
import com.example.aadhiStore.service.CompanyMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://aathithanstore.netlify.app"
})
@RequestMapping("/api/v1/company-master")
public class CompanyMasterController {

    @Autowired
    private CompanyMasterService companyMasterService;

    private static final Logger log = LoggerFactory.getLogger(CompanyMasterController.class);


    @GetMapping(value = "/list-view")
    private List<CompanyMaster> getAllCompany() {
        log.info("Fetched all entries successfully");
        return companyMasterService.getAllCompanyMaster();
    }

    @GetMapping(value = "/{id}")
    private CompanyMaster getCompanyById(@PathVariable Long id) {
        return companyMasterService.getCompanyById(id);
    }

    @PostMapping
    private ResponseEntity<CompanyMaster> createCompany(@RequestBody CompanyMaster companyMaster) {
        CompanyMaster createCompany = companyMasterService.createCompany(companyMaster);
        log.info("Entry created successfully" + companyMaster);
        return new ResponseEntity<>(createCompany, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    private ResponseEntity<CompanyMaster> updateCompany(@PathVariable Long id, @RequestBody CompanyMaster companyMaster) {
        CompanyMaster updatedCompany = companyMasterService.updateCompany(id, companyMaster);
        if (companyMaster != null) {
            log.info("Entry updated successfully" + companyMaster);
            return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
        }
        else {
            log.info("Entry not found" + companyMaster);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    private ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        companyMasterService.deleteCompany(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

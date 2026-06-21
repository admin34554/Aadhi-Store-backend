package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.InvalidInput;
import com.example.aadhiStore.entity.CompanyMaster;
import com.example.aadhiStore.repository.CompanyMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyMasterService {

    @Autowired
    private CompanyMasterRepository companyMasterRepository;


    public List<CompanyMaster> getAllCompanyMaster() {
        return companyMasterRepository.findAll();
    }

    public CompanyMaster getCompanyById(Long id) {
        return companyMasterRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Company not found for this id" + id));
    }

    public CompanyMaster createCompany(CompanyMaster companyMaster) {
        return companyMasterRepository.save(companyMaster);
    }

    public CompanyMaster updateCompany(Long id, CompanyMaster companyMaster) {
        if (companyMasterRepository.existsById(id)) {
            companyMaster.setId(id);
            return companyMasterRepository.save(companyMaster);
        } else {
            throw new InvalidInput("Company not found for this id" +id);
        }
    }

    public void deleteCompany(Long id) {
        companyMasterRepository.deleteById(id);
    }
}

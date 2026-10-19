package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.BranchMaster;
import com.example.aadhiStore.repository.BranchMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchMasterService {

    @Autowired
    private BranchMasterRepository branchMasterRepository;

    public BranchMasterService( BranchMasterRepository branchMasterRepository){
        this.branchMasterRepository = branchMasterRepository;
    }

    public List<BranchMaster> getAllBranch(){
        return branchMasterRepository.findAll();
    }

    public BranchMaster getBranchById(Long id){
        return branchMasterRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Branch not found" + id));
    }

    public BranchMaster createBranch(BranchMaster branchMaster){
        return branchMasterRepository.save(branchMaster);
    }

}

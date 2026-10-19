package com.example.aadhiStore.controller;

import com.example.aadhiStore.entity.BranchMaster;
import com.example.aadhiStore.service.BranchMasterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/branch-master")
public class BranchMasterController {

    public static final Logger log = LoggerFactory.getLogger(BranchMasterController.class);

    @Autowired
    private BranchMasterService branchMasterService;

    public BranchMasterController(BranchMasterService branchMasterService){
        this.branchMasterService = branchMasterService;
    }

    @GetMapping("/list-view")
    private List<BranchMaster> getAllBranch(){
        log.info("fetched all branches");
        return branchMasterService.getAllBranch();
    }

    @GetMapping("/{id}")
    private BranchMaster getBranchById(@PathVariable Long id){
        return branchMasterService.getBranchById(id);
    }

    @PostMapping
    public ResponseEntity<BranchMaster> createBranch(@RequestBody BranchMaster branchMaster){
        BranchMaster createBranch = branchMasterService.createBranch(branchMaster);
        log.info("branch updated successfully" + branchMaster);
        return new ResponseEntity<>(createBranch, HttpStatus.CREATED);

    }
}

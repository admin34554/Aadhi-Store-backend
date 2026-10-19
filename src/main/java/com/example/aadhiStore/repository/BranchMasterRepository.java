package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.BranchMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchMasterRepository extends JpaRepository<BranchMaster, Long> {
}

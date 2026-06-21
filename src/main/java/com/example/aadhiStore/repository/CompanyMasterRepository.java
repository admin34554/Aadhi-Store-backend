package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.CompanyMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyMasterRepository extends JpaRepository<CompanyMaster, Long> {
}

package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.CustomerMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerMasterRepository extends JpaRepository<CustomerMaster, Long> {

    List<CustomerMaster> findByCompanyMasterIdAndFullNameContainingIgnoreCaseOrCompanyMasterIdAndCodeContainingIgnoreCase(Long companyId1, String fullName, Long companyId2, String code);

    List<CustomerMaster> findByCompanyMasterId(Long companyId);
}

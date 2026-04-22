package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.CustomerMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerMasterRepository extends JpaRepository<CustomerMaster, Long> {

    List<CustomerMaster> findByFullNameContainingIgnoreCaseOrCodeContainingIgnoreCase(String fullName, String code);
}

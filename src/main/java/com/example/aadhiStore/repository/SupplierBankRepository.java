package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.SupplierBankDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierBankRepository extends JpaRepository<SupplierBankDetails, Long> {
}

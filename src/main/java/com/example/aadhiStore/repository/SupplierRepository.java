package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.SupplierMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<SupplierMaster, Long> {
}

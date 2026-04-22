package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.TaxMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxRepository extends JpaRepository<TaxMaster, Long> {
    List<TaxMaster> findByNameContainingIgnoreCaseOrTaxCodeContainingIgnoreCase(String name, String name1);
}

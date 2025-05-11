package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.TaxMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxRepository extends JpaRepository<TaxMaster, Long> {
}

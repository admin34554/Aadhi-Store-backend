package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.TaxMasterNew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaxMasterRepository extends JpaRepository<TaxMasterNew, Long> {

    List<TaxMasterNew> findByHsnCode(String code);

    TaxMasterNew findFirstByProductBrandNameContainingIgnoreCase(String productBrandName);
}

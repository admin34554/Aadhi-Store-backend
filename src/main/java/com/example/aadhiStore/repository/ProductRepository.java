package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.ProductMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductMaster, Long> {


    List<ProductMaster> findByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String value, String value1);

    Optional<ProductMaster> findByCode(String code);

    Long countByName(String productName);
}

package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.ProductMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductMaster, Long> {


    List<ProductMaster> findByCodeContainingIgnoreCaseOrNameContainingIgnoreCase(String value, String value1);
}

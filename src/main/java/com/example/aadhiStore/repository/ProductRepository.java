package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.ProductMaster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductMaster, Long> {
}

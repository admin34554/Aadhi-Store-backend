package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.ProductTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductTypeRepository extends JpaRepository<ProductTypeMaster, Long> {


    List<ProductTypeMaster> findByProductTypeContainingIgnoreCaseOrCodeContainingIgnoreCase(String name, String code);
}

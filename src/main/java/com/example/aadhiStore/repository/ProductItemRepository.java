package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    Optional<ProductItem> findByItemNameIgnoreCase(String itemName);
}

package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.ProductItemPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductItemPriceRepository extends JpaRepository<ProductItemPrice, Long> {


    Optional<ProductItemPrice> findByProductItemId(Long productItemId);}

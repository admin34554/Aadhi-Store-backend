package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.StockMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockMasterRepository extends JpaRepository<StockMaster,Long> {

    List<StockMaster> findByItemNameIgnoreCase(String itemName);

    Optional<StockMaster> findByitemNameIgnoreCase(String itemName);


    Optional<StockMaster> findByItemName(String itemName);

}

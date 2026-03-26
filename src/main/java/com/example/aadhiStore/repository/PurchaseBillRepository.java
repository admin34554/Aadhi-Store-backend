package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.PurchaseBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseBillRepository extends JpaRepository<PurchaseBill, Long> {
    List<PurchaseBill> findByPurchaseBillDateBetween(Date from, Date to);
    List<PurchaseBill> findByPurchaseBillDateAfter(Date from);
    List<PurchaseBill> findByPurchaseBillDateBefore(Date to);}

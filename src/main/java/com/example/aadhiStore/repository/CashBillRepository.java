package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.CashBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CashBillRepository extends JpaRepository<CashBill, Long> {

    List<CashBill> findByBillDateBetween(Date from, Date to);
    List<CashBill> findByBillDateAfter(Date from);
    List<CashBill> findByBillDateBefore(Date to);}

package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.CreditBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CreditBillRepository extends JpaRepository<CreditBill, Long> {
    CreditBill findByBillNo(String billNo);

    List<CreditBill> findByBillDateBetween(Date from, Date to);

    List<CreditBill> findByBillDateAfter(Date from);

    List<CreditBill> findByBillDateBefore(Date to);
}

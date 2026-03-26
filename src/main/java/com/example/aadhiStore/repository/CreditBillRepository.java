package com.example.aadhiStore.repository;

import com.example.aadhiStore.entity.CreditBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditBillRepository extends JpaRepository<CreditBill, Long> {
}

package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.BrokerMaster;
import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.repository.CashBillRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashBillService {

    @Autowired
    private final CashBillRepository cashBillRepository;

    public CashBillService(CashBillRepository cashBillRepository) {
        this.cashBillRepository = cashBillRepository;
    }

    public List<CashBill> getAllCashBills() {
        return cashBillRepository.findAll();
    }

    public CashBill getCashBillsById(Long id) {
        return cashBillRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Cash Bills not found" +id));
    }

    public CashBill createCashBill(CashBill cashBill) {
        return cashBillRepository.save(cashBill);
    }
}

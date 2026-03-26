package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.CreditBill;
import com.example.aadhiStore.repository.CreditBillRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditBillService {

    private final CreditBillRepository creditBillRepository;

    public CreditBillService(CreditBillRepository creditBillRepository) {
        this.creditBillRepository = creditBillRepository;
    }

    public List<CreditBill> getAllCrediBills() {
        return creditBillRepository.findAll();
    }

    public CreditBill getCreditBillsById(Long id) {
        return creditBillRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Credit Bills not found" +id));
    }

    public CreditBill createCreditBill(CreditBill creditBill) {
        return creditBillRepository.save(creditBill);
    }
}

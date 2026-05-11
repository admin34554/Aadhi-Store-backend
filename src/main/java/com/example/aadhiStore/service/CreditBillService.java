package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.CashBillItems;
import com.example.aadhiStore.entity.CreditBill;
import com.example.aadhiStore.entity.CreditBillItems;
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
        if (creditBill.getItems() != null) {
            for (CreditBillItems item : creditBill.getItems()) {
                item.setCreditBill(creditBill);
            }
        }
        return creditBillRepository.save(creditBill);
    }

    public CreditBill getCreditBillByBillNo(String billNo) {
        return creditBillRepository.findByBillNo(billNo);
        }
}

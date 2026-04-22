package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.entity.PurchaseBill;
import com.example.aadhiStore.repository.PurchaseBillRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseBillService {

    private final PurchaseBillRepository purchaseBillRepository;

    public PurchaseBillService(PurchaseBillRepository purchaseBillRepository) {
        this.purchaseBillRepository = purchaseBillRepository;
    }

    public List<PurchaseBill> getAllPurchaseBill() {
        return purchaseBillRepository.findAll();
    }

    public PurchaseBill getPurchaseBillById(Long id) {
        return purchaseBillRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Purchase Bill not found" +id));
    }

    public PurchaseBill createPurchaseBill(PurchaseBill purchaseBill) {
        purchaseBill.setPurchaseBillNo(null);
        return purchaseBillRepository.save(purchaseBill);
    }
}

package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.entity.CashBillItems;
import com.example.aadhiStore.entity.ProductMaster;
import com.example.aadhiStore.repository.CashBillRepository;
import com.example.aadhiStore.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashBillService {

    @Autowired
    private final CashBillRepository cashBillRepository;
    @Autowired
    private ProductRepository productRepository;

    public CashBillService(CashBillRepository cashBillRepository) {
        this.cashBillRepository = cashBillRepository;
    }

    public List<CashBill> getAllCashBills() {
        return cashBillRepository.findAll();
    }

    public CashBill getCashBillsById(Long id) {
        return cashBillRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cash Bills not found" + id));
    }

    public CashBill createCashBill(CashBill cashBill) {
        if (cashBill.getItems() != null) {
            cashBill.getItems().forEach(item -> item.setCashBill(cashBill));
        }
        for (CashBillItems item : cashBill.getItems()) {
            ProductMaster productMaster = productRepository.findByCode(item.getProductCode())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductCode()));
            double currentWeight = productMaster.getWeight() == null ? 0 : productMaster.getWeight();
            double soldWeight = item.getQuantity();
            if (currentWeight < soldWeight) {
                throw new RuntimeException("Insufficient stock for product" + productMaster.getName());
            }
            productMaster.setWeight(currentWeight - soldWeight);
            productRepository.save(productMaster);
        }
        return cashBillRepository.save(cashBill);
    }

    public CashBill getCashBillByBillNo(String billNo) {
        return cashBillRepository.findByBillNo(billNo);
    }
}

package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.entity.CashBillItems;
import com.example.aadhiStore.entity.ProductMaster;
import com.example.aadhiStore.entity.TaxMaster;
import com.example.aadhiStore.exception.StockExceptions;
import com.example.aadhiStore.repository.CashBillRepository;
import com.example.aadhiStore.repository.ProductRepository;
import com.example.aadhiStore.repository.TaxRepository;
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
    @Autowired
    private TaxRepository taxRepository;

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
            double currentWeight = productMaster.getNoOfPacks() == null ? 0 : productMaster.getNoOfPacks();
            double soldWeight = item.getQuantity();
            if (currentWeight < soldWeight) {
                throw new StockExceptions(" Insufficient Stock for Product" + productMaster.getName());
            }
            TaxMaster taxMaster = taxRepository.findFirstByNameContainingIgnoreCase(productMaster.getName());
            if (taxMaster == null) {
                double cgst = taxMaster.getCgst() == null ? 0 : Double.parseDouble(taxMaster.getCgst());
                double sgst = taxMaster.getSgst() == null ? 0 : Double.parseDouble(taxMaster.getSgst());
                item.setTax(String.valueOf(cgst + sgst));
                item.setBrNo(taxMaster.getHsnCode());
                item.setTax(taxMaster.getName());
            }
            productMaster.setNoOfPacks(currentWeight - soldWeight);
            productRepository.save(productMaster);
        }
        return cashBillRepository.save(cashBill);
    }

    public CashBill getCashBillByBillNo(String billNo) {
        return cashBillRepository.findByBillNo(billNo);
    }
}

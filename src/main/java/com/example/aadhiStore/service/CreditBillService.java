package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.CreditBill;
import com.example.aadhiStore.entity.CreditBillItems;
import com.example.aadhiStore.entity.ProductMaster;
import com.example.aadhiStore.entity.TaxMaster;
import com.example.aadhiStore.exception.StockExceptions;
import com.example.aadhiStore.repository.CreditBillRepository;
import com.example.aadhiStore.repository.ProductRepository;
import com.example.aadhiStore.repository.TaxRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditBillService {

    @Autowired
    private ProductRepository productRepository;

    private final CreditBillRepository creditBillRepository;

    @Autowired
    private TaxRepository taxRepository;

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
            creditBill.getItems().forEach(item -> item.setCreditBill(creditBill));
        }
        for (CreditBillItems item : creditBill.getItems()) {
            ProductMaster productMaster = productRepository.findByCode(item.getProductCode())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + item.getProductCode()));
            double currentWeight = productMaster.getWeight() == null ? 0 : productMaster.getWeight();
            double soldWeight = item.getQuantity();
            if (currentWeight < soldWeight) {
                throw new StockExceptions("Insufficient Stock for Product" + productMaster.getName());
            }
            TaxMaster taxMaster = taxRepository.findFirstByNameContainingIgnoreCase(productMaster.getName());
            if (taxMaster == null) {
                double cgst = taxMaster.getCgst() == null ? 0 : Double.parseDouble(taxMaster.getCgst());
                double sgst = taxMaster.getSgst() == null ? 0 : Double.parseDouble(taxMaster.getSgst());
                item.setTax(String.valueOf(cgst + sgst));
                item.setBrNo(taxMaster.getHsnCode());
                item.setTax(taxMaster.getName());
            }
            productMaster.setWeight(currentWeight - soldWeight);
            productRepository.save(productMaster);
        }
            return creditBillRepository.save(creditBill);
        }

    public CreditBill getCreditBillByBillNo(String billNo) {
        return creditBillRepository.findByBillNo(billNo);
        }
}

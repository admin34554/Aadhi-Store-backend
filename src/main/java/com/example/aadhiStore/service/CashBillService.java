package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.*;
import com.example.aadhiStore.exception.StockExceptions;
import com.example.aadhiStore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashBillService {

    @Autowired
    private final CashBillRepository cashBillRepository;
    @Autowired
    private ProductNewRepository productRepository;
    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private TaxMasterRepository taxMasterRepository;

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
            ProductMasterNew productMaster = productRepository.findByProductCode(item.getProductCode());
            ProductItem productItem = productMaster.getProductItems()
                    .stream()
                    .filter(pi -> pi.getId().equals(item.getProductItemId()))
                    .findFirst()
                    .orElseThrow(() ->
                            new RuntimeException("Product Item not found: " + item.getProductItemId()));
            ProductItemPrice productItemPrice = productItem.getProductItemPrice()
                    .stream().findFirst().orElseThrow(()-> new RuntimeException("Price Details not found"));
            long currentWeight = productItemPrice.getQuantity() == null ? 0 : productItemPrice.getQuantity();
            long soldWeight = item.getQuantity();
            if (currentWeight < soldWeight) {
                throw new StockExceptions(" Insufficient Stock for Product" + productMaster.getProductName());
            }

            productItemPrice.setQuantity(currentWeight - soldWeight);
            TaxMasterNew taxMaster = taxMasterRepository.findFirstByProductBrandNameContainingIgnoreCase(productMaster.getProductName());
            if (taxMaster != null) {
                double cgst = taxMaster.getCgst() == null ? 0 : taxMaster.getCgst();
                double sgst = taxMaster.getSgst() == null ? 0 : taxMaster.getSgst();

                item.setTax(String.valueOf(cgst + sgst));
                item.setBrNo(taxMaster.getHsnCode());
            }
            productRepository.save(productMaster);
        }
        return cashBillRepository.save(cashBill);
    }

    public CashBill getCashBillByBillNo(String billNo) {
        return cashBillRepository.findByBillNo(billNo);
    }
}

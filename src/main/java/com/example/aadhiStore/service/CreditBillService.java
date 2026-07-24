package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.*;
import com.example.aadhiStore.exception.StockExceptions;
import com.example.aadhiStore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditBillService {

    @Autowired
    private ProductNewRepository productRepository;

    private final CreditBillRepository creditBillRepository;

    @Autowired
    private TaxRepository taxRepository;

    @Autowired
    private TaxMasterRepository taxMasterRepository;

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
            ProductMasterNew productMaster = productRepository.findByProductCode(item.getProductCode());
            ProductItem productItem = productMaster.getProductItems()
                    .stream().filter(productItem1 -> productItem1.getItemName().equalsIgnoreCase(item.getItemName())).findFirst()
                    .orElseThrow(() -> new RuntimeException("Product Item not found: " + item.getItemName()));
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
            return creditBillRepository.save(creditBill);
        }

    public CreditBill getCreditBillByBillNo(String billNo) {
        return creditBillRepository.findByBillNo(billNo);
        }
}

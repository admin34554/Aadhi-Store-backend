package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.*;
import com.example.aadhiStore.repository.ProductNewRepository;
import com.example.aadhiStore.repository.ProductRepository;
import com.example.aadhiStore.repository.PurchaseBillRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseBillService {

    private final PurchaseBillRepository purchaseBillRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductNewRepository productNewRepository;

    public PurchaseBillService(PurchaseBillRepository purchaseBillRepository) {
        this.purchaseBillRepository = purchaseBillRepository;
    }

    public List<PurchaseBill> getAllPurchaseBill() {
        return purchaseBillRepository.findAll();
    }

    public PurchaseBill getPurchaseBillById(Long id) {
        return purchaseBillRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Purchase Bill not found" +id));
    }

    @Transactional
    public PurchaseBill createPurchaseBill(PurchaseBill purchaseBill) {
        purchaseBill.setPurchaseBillNo(null);

        if (purchaseBill.getItems() != null) {
            purchaseBill.getItems().forEach(item ->
                    item.setPurchaseBill(purchaseBill));
        }

        PurchaseBill savedBill = purchaseBillRepository.save(purchaseBill);
        if (savedBill.getItems() != null) {

            for (PurchaseItem item : savedBill.getItems()) {

                ProductMasterNew productMaster =
                        productNewRepository.findByProductCode(item.getProductCode());

                if (productMaster == null) {
                    throw new RuntimeException("Product not found : " + item.getProductCode());
                }

                ProductItem productItem = productMaster.getProductItems()
                        .stream()
                        .filter(pi -> pi.getId().equals(item.getProductItem().getId()))
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException("Product Item not found : "
                                        + item.getProductItem().getId()));

                ProductItemPrice price = productItem.getProductItemPrice()
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new RuntimeException("Price details not found"));

                long currentQty = price.getQuantity() == null ? 0 : price.getQuantity();
                long purchaseQty = item.getQuantity() == null ? 0 : item.getQuantity();

                price.setQuantity(currentQty + purchaseQty);
            }
            return purchaseBillRepository.save(savedBill);
        }

        return savedBill;
    }
}

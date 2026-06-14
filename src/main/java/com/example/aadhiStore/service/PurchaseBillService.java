package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.entity.PurchaseBill;
import com.example.aadhiStore.entity.PurchaseItem;
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

                productRepository.findByCode(item.getProductCode())
                        .ifPresent(product -> {

                            Double currentPacks =
                                    product.getNoOfPacks() == null
                                            ? 0.0
                                            : product.getNoOfPacks();

                            Double purchasedQty =
                                    item.getQuantity() == null
                                            ? 0.0
                                            : item.getQuantity();

                            product.setNoOfPacks(currentPacks + purchasedQty);

                            productRepository.save(product);
                        });
            }
        }

        return savedBill;
    }
}

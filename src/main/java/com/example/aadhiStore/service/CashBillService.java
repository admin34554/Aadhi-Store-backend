package com.example.aadhiStore.service;

import com.example.aadhiStore.entity.*;
import com.example.aadhiStore.exception.StockExceptions;
import com.example.aadhiStore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
    private ProductItemPriceRepository productItemPriceRepository;

    @Autowired
    private StockMasterRepository stockMasterRepository;

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

    @Transactional
    public CashBill createCashBill(CashBill cashBill) {

        if (cashBill.getItems() != null) {
            cashBill.getItems()
                    .forEach(item -> item.setCashBill(cashBill));
        }

        for (CashBillItems item : cashBill.getItems()) {

            ProductMasterNew productMaster = productRepository.findByProductCode(item.getProductCode());

            if (productMaster == null) {
                throw new RuntimeException(
                        "Product not found: " + item.getProductCode());
            }

            ProductItem productItem = productMaster.getProductItems()
                            .stream()
                            .filter(pi ->
                                    pi.getId().equals(item.getProductItemId()))
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException(
                                            "Product Item not found: " + item.getProductItemId()));

            ProductItemPrice productItemPrice = productItem.getProductItemPrice()
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Price Details not found"));
            Long productItemPriceId = productItemPrice.getId();

            long soldQuantity = item.getQuantity();

            long currentProductQuantity = productItemPrice.getQuantity() == null ? 0
                    : productItemPrice.getQuantity();


            if (currentProductQuantity < soldQuantity) {
                throw new StockExceptions(
                        "Insufficient Stock for Product "
                                + productMaster.getProductName() + ". Available: " + currentProductQuantity);
            }
            long remainingQuantity = currentProductQuantity - soldQuantity;

            productItemPrice.setQuantity(remainingQuantity);

            StockMaster stockMaster = stockMasterRepository
                    .findByItemName(productItem.getItemName())
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Stock not found for Item Name: "
                                            + productItem.getItemName()
                            ));

            long currentStockQuantity = stockMaster.getQuantity() == null ? 0 : stockMaster.getQuantity();


            if (currentStockQuantity < soldQuantity) {

                throw new StockExceptions("Insufficient Stock in Stock Master for Product "
                                + productMaster.getProductName()
                                + ". Available: " + currentStockQuantity);
            }
            long remainingStockQuantity = currentStockQuantity - soldQuantity;

            stockMaster.setQuantity(remainingStockQuantity);

            TaxMasterNew taxMaster = taxMasterRepository.findFirstByProductBrandNameContainingIgnoreCase(productMaster.getProductName());

            if (taxMaster != null) {

                double cgst = taxMaster.getCgst() == null ? 0 : taxMaster.getCgst();
                double sgst = taxMaster.getSgst() == null ? 0 : taxMaster.getSgst();
                item.setTax(String.valueOf(cgst + sgst));

                item.setBrNo(taxMaster.getHsnCode());
            }
            stockMasterRepository.save(stockMaster);

            productRepository.save(productMaster);
        }


        return cashBillRepository.save(cashBill);
    }

    public CashBill getCashBillByBillNo(String billNo) {
        return cashBillRepository.findByBillNo(billNo);
    }


    @Transactional
    public CashBill updateCashBill(Long id, CashBill cashBill) {


        CashBill existingBill = cashBillRepository.findById(id).orElseThrow(() -> new RuntimeException("Cash Bill not found with id: " + id));


        existingBill.setBillDate(cashBill.getBillDate());
        existingBill.setBillNo(cashBill.getBillNo());
        existingBill.setBroker(cashBill.getBroker());
        existingBill.setLorry(cashBill.getLorry());
        existingBill.setName(cashBill.getName());
        existingBill.setRemarks(cashBill.getRemarks());
        existingBill.getItems().clear();

        if (cashBill.getItems() != null) {

            for (CashBillItems item : cashBill.getItems()) {

                item.setId(null);

                item.setCashBill(existingBill);

                existingBill.getItems().add(item);
            }
        }

        return cashBillRepository.save(existingBill);
    }

    @Transactional
    public void deleteCashBill(Long id) {

        CashBill cashBill = cashBillRepository.findById(id).orElseThrow(() ->
                        new RuntimeException("Cash Bill not found with id: " + id));

        if (cashBill.getItems() != null) {

            for (CashBillItems item : cashBill.getItems()) {

                String itemName = item.getItemName();
                Long productItemId = item.getProductItemId();
                Long billQuantity = item.getQuantity();

                StockMaster stockMaster = stockMasterRepository.findByItemName(itemName).orElseThrow(() ->
                                new RuntimeException("Stock not found for Item Name: " + itemName));

                Long currentQuantity = stockMaster.getQuantity() == null ? 0L : stockMaster.getQuantity();

                stockMaster.setQuantity(currentQuantity + billQuantity);

                stockMasterRepository.save(stockMaster);

                ProductItemPrice productItemPrice = productItemPriceRepository.findByProductItemId(productItemId)
                                .orElseThrow(() ->
                                        new RuntimeException(
                                                "Product Item Price not found for Product Item ID: "
                                                        + productItemId));

                Long productQuantity =
                        productItemPrice.getQuantity() == null
                                ? 0L
                                : productItemPrice.getQuantity();

                productItemPrice.setQuantity(
                        productQuantity + billQuantity
                );

                productItemPriceRepository.save(productItemPrice);
            }


        }
        cashBillRepository.delete(cashBill);
    }
}

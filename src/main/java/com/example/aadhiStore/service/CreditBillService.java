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
public class CreditBillService {

    @Autowired
    private ProductNewRepository productRepository;

    private final CreditBillRepository creditBillRepository;

    @Autowired
    private ProductItemPriceRepository productItemPriceRepository;

    @Autowired
    private TaxMasterRepository taxMasterRepository;

    @Autowired
    private StockMasterRepository stockMasterRepository;

    public CreditBillService(CreditBillRepository creditBillRepository) {
        this.creditBillRepository = creditBillRepository;
    }

    public List<CreditBill> getAllCrediBills() {
        return creditBillRepository.findAll();
    }

    public CreditBill getCreditBillsById(Long id) {
        return creditBillRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Credit Bills not found" +id));
    }

    @Transactional
    public CreditBill createCreditBill(CreditBill creditBill) {

        if (creditBill.getItems() != null) {
            creditBill.getItems()
                    .forEach(item -> item.setCreditBill(creditBill));
        }

        for (CreditBillItems item : creditBill.getItems()) {

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
                        "Insufficient Stock for Product " + productMaster.getProductName() + ". Available: " + currentProductQuantity);
            }
            long remainingQuantity = currentProductQuantity - soldQuantity;

            productItemPrice.setQuantity(remainingQuantity);

            StockMaster stockMaster = stockMasterRepository
                    .findByItemName(productItem.getItemName())
                    .orElseThrow(() ->
                            new RuntimeException("Stock not found for Item Name: " + productItem.getItemName()));

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


        return creditBillRepository.save(creditBill);
    }

    public CreditBill getCreditBillByBillNo(String billNo) {
        return creditBillRepository.findByBillNo(billNo);
        }


    @Transactional
    public CreditBill updateCreditBill(Long id, CreditBill creditBill) {


        CreditBill existingBill = creditBillRepository.findById(id).orElseThrow(() -> new RuntimeException("Credit Bill not found with id: " + id));


        existingBill.setBillDate(creditBill.getBillDate());
        existingBill.setBillNo(creditBill.getBillNo());
        existingBill.setBroker(creditBill.getBroker());
        existingBill.setLorry(creditBill.getLorry());
        existingBill.setName(creditBill.getName());
        existingBill.setRemarks(creditBill.getRemarks());
        existingBill.getItems().clear();

        if (creditBill.getItems() != null) {

            for (CreditBillItems item : creditBill.getItems()) {

                item.setId(null);

                item.setCreditBill(existingBill);

                existingBill.getItems().add(item);
            }
        }

        return creditBillRepository.save(existingBill);
    }

    @Transactional
    public void deleteCreditBill(Long id) {

        CreditBill creditBill = creditBillRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Cash Bill not found with id: " + id));

        if (creditBill.getItems() != null) {

            for (CreditBillItems item : creditBill.getItems()) {

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
        creditBillRepository.delete(creditBill);
    }
}

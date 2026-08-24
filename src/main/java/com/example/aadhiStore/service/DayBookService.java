package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.CashBillDTO;
import com.example.aadhiStore.dto.CreditBillDTO;
import com.example.aadhiStore.dto.DayBookDTO;
import com.example.aadhiStore.dto.PurchaseBillDTO;
import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.entity.CreditBill;
import com.example.aadhiStore.entity.PurchaseBill;
import com.example.aadhiStore.repository.CashBillRepository;
import com.example.aadhiStore.repository.CreditBillRepository;
import com.example.aadhiStore.repository.PurchaseBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class DayBookService {


    @Autowired
    private CashBillRepository cashBillRepository;

    @Autowired
    private CreditBillRepository creditBillRepository;

    @Autowired
    private PurchaseBillRepository purchaseBillRepository;


    public DayBookDTO getDayBook(Date from, Date to) {


        if (to != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(to);
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 999);
            to = cal.getTime();
        }

        List<CashBill> cashBills;
        List<CreditBill> creditBills = List.of();
        List<PurchaseBill> purchaseBills;

            if (from != null && to != null) {
                cashBills = cashBillRepository.findByBillDateBetween(from, to);
                creditBills = creditBillRepository.findByBillDateBetween(from, to);
                purchaseBills = purchaseBillRepository.findByPurchaseBillDateBetween(from, to);

            } else if (from != null) {
                cashBills = cashBillRepository.findByBillDateAfter(from);
                creditBills = creditBillRepository.findByBillDateAfter(from);
                purchaseBills = purchaseBillRepository.findByPurchaseBillDateAfter(from);

            } else if (to != null) {
                cashBills = cashBillRepository.findByBillDateBefore(to);
                creditBills = creditBillRepository.findByBillDateBefore(to);
                purchaseBills = purchaseBillRepository.findByPurchaseBillDateBefore(to);

            } else {
                cashBills = cashBillRepository.findAll();
                purchaseBills = purchaseBillRepository.findAll();
            }

        List<CashBillDTO> cashBillDTOList = cashBills.stream()
                .map(bill -> new CashBillDTO(
                        bill.getId(),
                        bill.getBillNo(),
                        bill.getBillDate(),
                        bill.getRemarks(),
                        bill.getName()
                ))
                .toList();


            List<CreditBillDTO> creditBillDTOList = creditBills.stream()
                    .map(bill -> new CreditBillDTO(
                            bill.getId(),
                            bill.getBillNo(),
                            bill.getBillDate(),
                            bill.getRemarks(),
                            bill.getName()
                    ))
                    .toList();


        // Convert PurchaseBill → PurchaseBillDTO
        List<PurchaseBillDTO> purchaseBillDTOList = purchaseBills.stream()
                .map(bill -> new PurchaseBillDTO(
                        bill.getId(),
                        bill.getPurchaseBillDate(),
                        bill.getPurchaseBillNo(),
                        bill.getRemarks(),
                        bill.getName()
                ))
                .toList();

        DayBookDTO dtos = new DayBookDTO();

        dtos.setCashBillList(cashBillDTOList);
        dtos.setPurchaseBillList(purchaseBillDTOList);

        // Until credit bills are fetched
        dtos.setCreditBillsList(Collections.emptyList());

        return dtos;
        }
}

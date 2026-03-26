package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.DayBookDTO;
import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.entity.PurchaseBill;
import com.example.aadhiStore.repository.CashBillRepository;
import com.example.aadhiStore.repository.PurchaseBillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DayBookService {


    @Autowired
    private CashBillRepository cashBillRepository;

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
        List<PurchaseBill> purchaseBills;

            if (from != null && to != null) {
                cashBills = cashBillRepository.findByBillDateBetween(from, to);
                purchaseBills = purchaseBillRepository.findByPurchaseBillDateBetween(from, to);

            } else if (from != null) {
                cashBills = cashBillRepository.findByBillDateAfter(from);
                purchaseBills = purchaseBillRepository.findByPurchaseBillDateAfter(from);

            } else if (to != null) {
                cashBills = cashBillRepository.findByBillDateBefore(to);
                purchaseBills = purchaseBillRepository.findByPurchaseBillDateBefore(to);

            } else {
                cashBills = cashBillRepository.findAll();
                purchaseBills = purchaseBillRepository.findAll();
            }

            DayBookDTO dto = new DayBookDTO();
            dto.setCashBillList(cashBills);
            dto.setPurchaseBillList(purchaseBills);

            return dto;
        }

}

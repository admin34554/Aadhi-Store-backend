package com.example.aadhiStore.dto;


import com.example.aadhiStore.entity.CashBill;
import com.example.aadhiStore.entity.CreditBill;
import com.example.aadhiStore.entity.PurchaseBill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DayBookDTO {

    private List<PurchaseBillDTO> purchaseBillList;
    private List<CashBillDTO> cashBillList;
    private List<CreditBillDTO> creditBillsList;

}

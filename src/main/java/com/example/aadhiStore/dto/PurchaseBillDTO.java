package com.example.aadhiStore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class PurchaseBillDTO {

    private Long id;
    private String name;
    private String purchaseType;
    private String purchaseBillNo;
    private Date purchaseBillDate;
    private String remarks;

    public PurchaseBillDTO(Long id, Date purchaseBillDate, String purchaseBillNo, String remarks, String name) {

        this.id = id;
        this.purchaseBillDate = purchaseBillDate;
        this.purchaseBillNo = purchaseBillNo;
        this.remarks = remarks;

    }
}

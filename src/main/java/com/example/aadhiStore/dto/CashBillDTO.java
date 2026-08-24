package com.example.aadhiStore.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class CashBillDTO {

    private Long id;
    private String billNo;
    private Date billDate;
    private String name;
    private String remarks;

    public CashBillDTO(Long id, String billNo, Date billDate, String remarks, String name) {

        this.id = id;
        this.billNo = billNo;
        this.billDate = billDate;
        this.remarks = remarks;
        this.name = name;
    }
}

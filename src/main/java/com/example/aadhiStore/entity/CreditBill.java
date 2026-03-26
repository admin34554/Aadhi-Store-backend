package com.example.aadhiStore.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "credit_bill")
public class CreditBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "lorry_id")
    private LorryMaster lorry;

    @ManyToOne
    @JoinColumn(name = "broker_id")
    private BrokerMaster broker;

    @Column(name = "bill_no")
    private String billNo;

    @Column(name = "bill_date")
    private Date billDate;

    @OneToMany(mappedBy = "creditBill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CreditBillItems> items;

    @Column(name = "remarks")
    private String remarks;
}

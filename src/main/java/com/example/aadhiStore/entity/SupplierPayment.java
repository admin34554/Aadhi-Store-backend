package com.example.aadhiStore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "supplier_payment")
@Getter
@Setter
@NoArgsConstructor
public class SupplierPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private SupplierMaster supplierMaster;

    @Column(name = "account_no")
    private String accountNo;

    @Column(name = "payment_date")
    private LocalDateTime paymentDate;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @Column(name = "type")
    private String type;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "remarks")
    private String remarks;

}

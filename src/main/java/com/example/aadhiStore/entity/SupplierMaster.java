package com.example.aadhiStore.entity;


import com.example.aadhiStore.dto.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "supplier_master")
public class SupplierMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contact_person")
    private String contact;

    @Column(name = "aadhar")
    private Long aadharNo;

    @Column(name = "gst_no")
    private String gstNo;

    @Column(name = "pan_no")
    private String panNo;

    @Column(name = "phone")
    private Long phone;

    @Column(name = "mobile")
    private Long mobile;

    @Column(name = "credit_period")
    private Double creditPeriod;

    @Column(name = "door_no")
    private String doorNo;


    @Column(name = "street")
    private String street;

    @Column(name = "area")
    private String area;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pin_code")
    private Long pinCode;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

}

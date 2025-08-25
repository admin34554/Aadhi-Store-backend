package com.example.aadhiStore.entity;


import com.example.aadhiStore.dto.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_master")
public class CustomerMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String fullName;

    @Column(name = "door_no")
    private String doorNumber;

    @Column(name = "street_name")
    private String street;

    @Column(name = "area")
    private String area;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "pin_code")
    private Long pinCode;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "mobile_number")
    private Long mobileNumber;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "aadhar_number")
    private Long aadharNumber;

    @Column(name = "sugar_lic_no")
    private String sugarLicense;

    @Column(name = "credit_period")
    private Long creditPeriod;

    @Column(name = "credit_limit")
    private Double creditLimit;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;
}

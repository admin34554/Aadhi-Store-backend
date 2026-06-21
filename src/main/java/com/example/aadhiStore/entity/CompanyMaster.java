package com.example.aadhiStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company_master")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CompanyMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "gst_in", length = 15)
    private String gstIn;

    @Column(name = "pan", length = 10)
    private String pan;

    @Column(name = "address_line_1", length = 100)
    private String addressLine1;

    @Column(name = "address_line_2", length = 200)
    private String addressLine2;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country", length = 3)
    private String country;

    @Column(name = "phone", length = 100)
    private String phone;

    @Column(name = "email")
    private String email;
}

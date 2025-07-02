package com.example.aadhiStore.entity;

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
@Table(name = "lorry_master")
public class LorryMaster {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "area_covering")
    private String areaCovering;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "phone_number")
    private Long phoneNumber;

    @Column(name = "mobile_number")
    private Long mobileNumber;

}

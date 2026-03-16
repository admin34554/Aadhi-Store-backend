package com.example.aadhiStore.entity;

import com.example.aadhiStore.dto.Route;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @Column(name = "route_covered")
    @Enumerated(EnumType.STRING)
    private List<Route> routeCovering;

}

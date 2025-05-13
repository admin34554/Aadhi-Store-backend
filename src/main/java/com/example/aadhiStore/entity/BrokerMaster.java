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
@Table(name = "broker_master")
public class BrokerMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private Long code;

    @Column(name = "broker_name")
    private String brokerName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private Long phoneNo;

    @Column(name = "mobile")
    private Long mobileNo;

    @Column(name = "local_comm")
    private Double localComm;

    @Column(name = "out_comm")
    private Double outComm;

}

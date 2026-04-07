package com.example.aadhiStore.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BrokerDTO {

    private Long id;
    private String brokerName;
    private Long code;
}

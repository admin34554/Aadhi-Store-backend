package com.example.aadhiStore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private String token;
    private Long companyId;
    private String companyName;
    private String username;
    private String role;
}

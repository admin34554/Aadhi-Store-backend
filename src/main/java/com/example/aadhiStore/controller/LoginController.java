package com.example.aadhiStore.controller;


import com.example.aadhiStore.dto.LoginRequestDTO;
import com.example.aadhiStore.dto.LoginResponseDTO;
import com.example.aadhiStore.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

    @Autowired
    AuthenticationService authenticationService;

   @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {

       System.out.println("===== LOGIN API HIT =====");
       return authenticationService.login(loginRequestDTO);
   }

}

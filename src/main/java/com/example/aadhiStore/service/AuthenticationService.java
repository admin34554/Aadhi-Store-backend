package com.example.aadhiStore.service;

import com.example.aadhiStore.dto.LoginRequestDTO;
import com.example.aadhiStore.dto.LoginResponseDTO;
import com.example.aadhiStore.entity.UserMaster;
import com.example.aadhiStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO request){

        UserMaster user =
                userRepository.findByUsername(request.getUsername())
                        .orElseThrow(() ->
                                new BadCredentialsException("Invalid username/password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new BadCredentialsException("Invalid username/password");
        }

        String token =
                jwtService.generateToken(user);

        LoginResponseDTO response =
                new LoginResponseDTO();

        response.setToken(token);

        response.setUsername(user.getUsername());

        response.setCompanyId(user.getCompanyMaster().getId());

        response.setCompanyName(
                user.getCompanyMaster().getName());

        response.setRole(user.getRole());

        return response;

    }

}

package com.example.boubyantask.services;
import com.example.boubyantask.config.TokenResponse;
import org.springframework.security.core.Authentication;


public interface LoginService {

    TokenResponse auth(String request);

    TokenResponse refreshToken(String refreshToken);
}

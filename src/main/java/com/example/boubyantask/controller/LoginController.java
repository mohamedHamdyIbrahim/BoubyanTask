package com.example.boubyantask.controller;

import com.example.boubyantask.config.TokenResponse;
import com.example.boubyantask.dto.CoursesDto;
import com.example.boubyantask.dto.UserDto;
import com.example.boubyantask.repo.UserRepo;
import com.example.boubyantask.services.LoginService;
import com.example.boubyantask.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;
    private final UserService  userService;
    private final UserRepo userRepo;

    @GetMapping("/token")
    public TokenResponse Token(@RequestHeader HttpHeaders headers) {
        final String authorization = headers.getFirst("authorization");
        System.err.println("auth " + authorization);
        return loginService.auth(authorization);
    }
    @GetMapping("/refreshToken")
    public TokenResponse refreshToken(@RequestHeader HttpHeaders headers) {
        final String authorization = headers.getFirst("authorization");
        System.err.println("auth " + authorization);
        return loginService.refreshToken(authorization.split(" ")[1]);
    }
    @PostMapping ("/signup")
    public void signUp(@RequestBody UserDto userDto) {
        //userService.addUser(userDto);
        userRepo.findUserByLoginname(userDto.getLoginname());
    }


}

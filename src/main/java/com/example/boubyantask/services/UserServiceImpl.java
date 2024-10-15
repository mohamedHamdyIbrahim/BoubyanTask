package com.example.boubyantask.services;

import com.example.boubyantask.dto.UserDto;
import com.example.boubyantask.entities.User;
import com.example.boubyantask.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

private UserRepo userRepo;
private PasswordEncoder passwordEncoder;
    @Override
    public void addUser(UserDto userDto) {
        User newUser = User.builder().password(passwordEncoder.encode(userDto.getPassword())).username(userDto.getUsername()).loginname(userDto.getLoginname()).build();
        userRepo.saveAndFlush(newUser);
    }
}

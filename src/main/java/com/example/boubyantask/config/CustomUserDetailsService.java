package com.example.boubyantask.config;


import com.example.boubyantask.entities.User;
import com.example.boubyantask.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
//        System.err.println("inside custom method  "+loginName);
//        User user1 = userRepository.findUserByLoginname(loginName.trim());
//        System.err.println("this is user"+user1.getLoginname());

        User user = userRepository.findUserByLoginname(loginName);

        if (user == null) {
            throw  new UsernameNotFoundException("User not found: " + loginName);
        }

        // Build and return the UserDetails object
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLoginname())
                .password("{bcrypt}" + user.getPassword())
                .authorities("read")
                .build();
    }



}
package com.example.boubyantask.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "USERNAME", length = 50)
    private String username;

    @Column(name = "PASSWORD", length = 50)
    private String password;


    @Column(name = "LOGINNAME",unique = true, length = 50)
    private String loginname;

    
    @ManyToMany(mappedBy = "users")
    private Set<Courses> courses = new LinkedHashSet<>();

}
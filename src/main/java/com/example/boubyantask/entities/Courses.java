package com.example.boubyantask.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "COURSES")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "COURSEDESC", length = 200)
    private String coursedesc;

    @ManyToMany
    @JoinTable(name = "USERSCOURSES",
            joinColumns = @JoinColumn(name = "COURSEID"),
            inverseJoinColumns = @JoinColumn(name = "USERID"))
    private Set<User> users = new LinkedHashSet<>();

    @OneToMany(mappedBy = "courseid")
    private Set<Courseschedule> courseschedules = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courses courses = (Courses) o;
        return Objects.equals(id, courses.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
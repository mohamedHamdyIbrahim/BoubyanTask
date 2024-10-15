package com.example.boubyantask.repo;

import com.example.boubyantask.entities.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepo extends JpaRepository<Courses,Integer> {

    Courses findCoursesById(int id);
}

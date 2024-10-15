package com.example.boubyantask.services;

import com.example.boubyantask.dto.CoursesDto;

import java.util.List;

public interface CourseService {
    List<CoursesDto> findAll();
    void registerCourse(String loginName,int courseID);

    void unRegisterCourse(String loginName, int courseID);

    String createPDF(int courseID);
}

package com.example.boubyantask.mapper;

import com.example.boubyantask.dto.CoursesDto;
import com.example.boubyantask.entities.Courses;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper
public interface CoursesMapper {
    Courses toEntity(CoursesDto coursesDto);

    CoursesDto toDto(Courses courses);
    List<CoursesDto> toDto(List<Courses> courses);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    Courses partialUpdate(CoursesDto coursesDto, @MappingTarget Courses courses);
}
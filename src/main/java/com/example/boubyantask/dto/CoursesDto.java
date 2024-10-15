package com.example.boubyantask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * DTO for {@link com.example.boubyantask.entities.Courses}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CoursesDto implements Serializable {
    private Long id;
    private String name;
    private String coursedesc;
}
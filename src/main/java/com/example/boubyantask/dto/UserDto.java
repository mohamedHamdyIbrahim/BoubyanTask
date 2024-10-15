package com.example.boubyantask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * DTO for {@link com.example.boubyantask.entities.User}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDto implements Serializable {
    private Long id;
    private String username;
    private String password;
    private String loginname;
    private ArrayList<CoursesDto> courses = new ArrayList<>();
}
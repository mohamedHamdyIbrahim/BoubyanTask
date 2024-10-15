package com.example.boubyantask.mapper;

import com.example.boubyantask.dto.UserDto;
import com.example.boubyantask.entities.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper {
    User toEntity(UserDto userDto);

    UserDto toDto(User user);

//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    User partialUpdate(UserDto userDto, @MappingTarget User user);
}
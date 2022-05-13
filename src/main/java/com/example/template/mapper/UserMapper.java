package com.example.template.mapper;

import com.example.template.dto.RegisterUserInfoDto;
import com.example.template.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AppUser toUser(RegisterUserInfoDto dto);
}

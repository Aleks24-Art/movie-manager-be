package com.movie.manager.mapper;

import com.movie.manager.dto.AppUserDto;
import com.movie.manager.dto.RegisterUserInfoDto;
import com.movie.manager.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AppUser toUser(RegisterUserInfoDto dto);

    AppUserDto toDto(AppUser user);
}

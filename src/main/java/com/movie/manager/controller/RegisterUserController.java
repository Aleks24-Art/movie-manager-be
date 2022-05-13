package com.movie.manager.controller;

import com.movie.manager.dto.AppUserDto;
import com.movie.manager.dto.RegisterUserInfoDto;
import com.movie.manager.entity.AppUser;
import com.movie.manager.mapper.UserMapper;
import com.movie.manager.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize(value = "permitAll()")
public class RegisterUserController {

    private final AppUserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDto registerUser(@RequestBody RegisterUserInfoDto dto) {
        AppUser registeredUser = userService.registerUser(userMapper.toUser(dto));
        return userMapper.toDto(registeredUser);
    }
}

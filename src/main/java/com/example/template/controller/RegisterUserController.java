package com.example.template.controller;

import com.example.template.dto.RegisterUserInfoDto;
import com.example.template.entity.AppUser;
import com.example.template.mapper.UserMapper;
import com.example.template.service.AppUserService;
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
    public AppUser saveRole(@RequestBody RegisterUserInfoDto dto) {
        return userService.registerUser(userMapper.toUser(dto));
    }
}

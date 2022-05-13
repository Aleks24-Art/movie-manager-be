package com.movie.manager.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.movie.manager.dto.AddRoleToUserDto;
import com.movie.manager.entity.AppRole;
import com.movie.manager.entity.AppUser;
import com.movie.manager.service.AppUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class AppUserController {

    private final AppUserService userService;

    @GetMapping("/users")
    @PreAuthorize(value = "permitAll()")
    public List<AppUser> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/user/{username}")
    @PreAuthorize(value = "hasAuthority('ROLE_USER')")
    public AppUser getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping("/role/{roleName}")
    @PreAuthorize(value = "hasAuthority('ROLE_USER')")
    public AppRole getRole(@PathVariable String roleName) {
        return userService.getRole(roleName);
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(value = "hasAuthority('ROLE_MANAGER')")
    public AppUser saveUser(@RequestBody AppUser user) {
        return userService.saveUser(user);
    }

    @PostMapping("/role")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(value = "hasAuthority('ROLE_MANAGER')")
    public AppRole saveRole(@RequestBody AppRole appRole) {
        return userService.saveRole(appRole);
    }

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @PostMapping("/role-to-user")
    public void addRoleToUser(@RequestBody AddRoleToUserDto dto) {
        userService.addUserToRole(dto.getUsername(), dto.getRoleName());
    }

    @PreAuthorize(value = "hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/user/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserByUsername(@PathVariable String username) {
        userService.deleteUserByUsername(username);
    }

    @SneakyThrows
    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (Objects.nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secretWordForJWT".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                AppUser user = userService.getUser(username);
                String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(AppRole::getName).collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                log.error("Error logging in: {}", e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("Refresh token is missing!");
        }
    }

}

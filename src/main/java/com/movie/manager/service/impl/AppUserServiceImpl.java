package com.movie.manager.service.impl;


import com.movie.manager.entity.AppRole;
import com.movie.manager.entity.AppUser;
import com.movie.manager.exception.UserAlreadyRegisteredException;
import com.movie.manager.repository.AppUserRepo;
import com.movie.manager.repository.UserRoleRepo;
import com.movie.manager.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    private final AppUserRepo userRepo;
    private final UserRoleRepo userRoleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = getUser(username);
        List<SimpleGrantedAuthority> authority = new ArrayList<>();
        appUser.getRoles().forEach(
                role -> authority.add(new SimpleGrantedAuthority(role.getName()))
        );
        return new User(appUser.getUsername(), appUser.getPassword(), authority);
    }

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public AppUser registerUser(AppUser user) {
        userRepo.findByUsername(user.getUsername())
                .ifPresent(appUser -> {
                    throw new UserAlreadyRegisteredException(
                            "User with username: " + appUser.getUsername() + " already exist!"
                    );
                });

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser registered = userRepo.save(user);
        addUserToRole(registered.getUsername(), "ROLE_USER");
        return registered;
    }

    @Override
    public AppUser getUser(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public List<AppUser> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public AppRole saveRole(AppRole appRole) {
        return userRoleRepo.save(appRole);
    }

    @Override
    public AppRole getRole(String roleName) {
        return userRoleRepo.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found!"));
    }

    @Override
    public void addUserToRole(String username, String roleName) {
        AppUser appUser = getUser(username);
        AppRole appRole = getRole(roleName);
        appUser.getRoles().add(appRole);
    }

    @Override
    public void deleteUserByUsername(String username) {
        userRepo.deleteByUsername(username);
    }
}

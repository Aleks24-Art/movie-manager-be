package com.movie.manager.service;


import com.movie.manager.entity.AppUser;
import com.movie.manager.entity.AppRole;

import java.util.List;

public interface AppUserService {

    AppUser saveUser(AppUser user);

    AppUser registerUser(AppUser user);

    AppUser getUser(String username);

    List<AppUser> getAllUsers();

    AppRole saveRole(AppRole appRole);

    AppRole getRole(String roleName);

    void addUserToRole(String username, String rolename);

    void deleteUserByUsername(String username);
}

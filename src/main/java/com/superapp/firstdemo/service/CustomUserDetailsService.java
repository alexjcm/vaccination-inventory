package com.superapp.firstdemo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.superapp.firstdemo.model.User;
import com.superapp.firstdemo.model.Role;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user1 = userService.getUserByUsername(username);
        if (user1 == null) {
            throw new UsernameNotFoundException("Could not find user: " + username);
        }

        List<Role> roles = user1.getRoles();
        List<GrantedAuthority> authorities = roles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getName().toString());
        }).collect(Collectors.toList());
        org.springframework.security.core.userdetails.User usr = new org.springframework.security.core.userdetails.User(
                user1.getUsername(), user1.getPassword(), authorities);

        //OLD JWT ===> "authorities": "ROLE_USER",
        //NEW JWT ===> "roles": "ROLE_ADMIN ROLE_USER",
        return usr;
    }

}
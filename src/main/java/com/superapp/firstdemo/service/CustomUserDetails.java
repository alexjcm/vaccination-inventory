package com.superapp.firstdemo.service;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.superapp.firstdemo.model.Role;
import com.superapp.firstdemo.rest.AuthenticationRestController;
import com.superapp.firstdemo.model.User;

// tipo model
public class CustomUserDetails implements UserDetails {

    private User user;

    private static final Logger logger = Logger.getLogger(AuthenticationRestController.class.getName());

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roles = user.getRoles();
        List<GrantedAuthority> authorities = roles.stream().map(r -> {
            logger.info("r.getName().toString() ===> " + r.getName().toString());
            return new SimpleGrantedAuthority(r.getName().toString());
        }).collect(Collectors.toList());

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*@Override
    public boolean isEnabled() {
        return user.isEnabled();
    }*/

    // Cual el objeto no tiene el atributo enabled se usa esta opción y
    // no el anterior.
    @Override
    public boolean isEnabled() {
        return true;
    }


}
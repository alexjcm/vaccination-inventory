package com.superapp.firstdemo.service;

import com.superapp.firstdemo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.superapp.firstdemo.model.User;
import com.superapp.firstdemo.dao.UserDao;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    //private UserRepository userRepository;
    private UserDao userDao;

/*    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user1 = userDao.getUserByUsername(username);
        if (user1 == null) {
            throw new UsernameNotFoundException("Could not find user: " + username);
        }
        return new CustomUserDetails(user1);
    }*/

    //// add
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user1 = userDao.getUserByUsername(username);
        if (user1 == null) {
            throw new UsernameNotFoundException("Could not find user: " + username);
        }

        List<Role> roles = user1.getRoles();
        List<GrantedAuthority> authorities = roles.stream().map(r -> {
            return new SimpleGrantedAuthority(r.getName().toString());
        }).collect(Collectors.toList());
        org.springframework.security.core.userdetails.User usr = new org.springframework.security.core.userdetails.User(
                user1.getUsername(), user1.getPassword(), authorities);

        return usr;
    }

    /*private Collection<GrantedAuthority> getGrantedAuthority(User user) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (user.getRole().getName().equalsIgnoreCase("admin")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }*/
}
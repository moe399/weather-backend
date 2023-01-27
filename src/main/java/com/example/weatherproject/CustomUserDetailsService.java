package com.example.weatherproject;

import com.example.weatherproject.user.UserEntity;
import com.example.weatherproject.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    UserEntity user = userService.getUserFromDB(username);


    // Every user is assigned a user role

    Collection<GrantedAuthority> grantedAuthorityCollection = new ArrayList<>();

    grantedAuthorityCollection.add(new SimpleGrantedAuthority("USER"));

    return new User(user.getUsername(), user.getPassword(), grantedAuthorityCollection);


    }
}

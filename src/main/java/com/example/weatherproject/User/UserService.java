package com.example.weatherproject.User;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserEntity loadUserByUsername(String username){

     return  userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

    }






}

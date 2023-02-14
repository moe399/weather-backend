package com.example.weatherproject.User;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    //Read
    public UserEntity loadUsername(String username){

     return  userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

    }



}

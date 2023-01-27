package com.example.weatherproject.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;



    //Create



    //Read
    public UserEntity getUserFromDB(String username){

     return  userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));




    }


    //Update




    //Delete


}

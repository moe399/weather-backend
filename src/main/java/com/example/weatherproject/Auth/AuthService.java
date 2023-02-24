package com.example.weatherproject.Auth;


import com.example.weatherproject.Config.JwtService;
import com.example.weatherproject.User.UserEntity;
import com.example.weatherproject.User.UserRepository;
import com.example.weatherproject.User.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;


    public String Register(UserEntity userEntity){

        if(userRepository.existsByUsername(userEntity.getUsername()).orElseThrow() == true){
            return "unable to saved, username exists";
        }


        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));


        userRepository.save(userEntity);

        return "User saved";

    }

    public String Authenticate(UserEntity userRequest) {


        // authenticate
        // return jwt


            try {
                System.out.println("reachecd this controller");



                userRequest.setRole(userService.loadUserByUsername(userRequest.getUsername()).getRole());
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword(), userRequest.getAuthorities()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println(userRequest.getAuthorities());

                String user = userService.loadUserByUsername(userRequest.getUsername()).getUsername();


                System.out.println("Authorities are:"+ SecurityContextHolder.getContext().getAuthentication().getAuthorities());

                String JwtToken = jwtService.generateToken(user);

                return JwtToken;

            } catch (Exception e) {
                System.out.println(e);
                return "unable to authenticate";
            }









    }





}

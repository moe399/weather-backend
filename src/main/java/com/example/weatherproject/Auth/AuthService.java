package com.example.weatherproject.Auth;


import com.example.weatherproject.Config.JwtService;
import com.example.weatherproject.User.UserEntity;
import com.example.weatherproject.User.UserRepository;
import com.example.weatherproject.User.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;


    public String Register(UserEntity userEntity){


        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);

        return "User saved";

    }

    public String Authenticate(UserEntity userRequest) {

        // authenticate
        // return jwt

        if (!userService.loadUserByUsername(userRequest.getUsername()).getUsername().isEmpty()) {


            try {

                Authentication authentication = authenticationManager.authenticate(SecurityContextHolder.getContext().getAuthentication());

          String JwtToken = jwtService.generateToken(authentication.getName());

          return JwtToken;

            } catch (Exception e) {
                System.out.println(e);
            }

        }


        else{
            return "Couldn't authenticate";


        }



    }





}

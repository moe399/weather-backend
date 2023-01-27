package com.example.weatherproject;

import com.example.weatherproject.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {



    @Autowired
    UserService userService;


   //create constructor with userservice as paramateter



    //get user details

    //use password encoder

    //use default authneticationprovider



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{


        http.authorizeHttpRequests().requestMatchers("/user").permitAll().and().csrf().disable().formLogin();



        return http.build();

    }



}

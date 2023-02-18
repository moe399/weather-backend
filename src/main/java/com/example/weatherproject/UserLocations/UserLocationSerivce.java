package com.example.weatherproject.UserLocations;

import com.example.weatherproject.User.UserEntity;
import com.example.weatherproject.User.UserRepository;
import com.example.weatherproject.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLocationSerivce {

    private final UserLocationRepository userLocationRepository;

    private final UserService userService;
    private final UserRepository userRepository;


    // SAVE

    public ResponseEntity<String> saveLocation(UserLocation userLocation){


        userLocation.setUserEntity(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow());





        userLocationRepository.save(userLocation);

        return ResponseEntity.ok(userLocation.getCityName() + "by" + userLocation.getUserEntity().getUsername());

    }



    // READ
    public List<String> readLocationsByUsername(String username){


        UserEntity user = userService.loadUserByUsername(username);
        System.out.println("Reached user list service location");

        List<String> userLocationList = new ArrayList<>();

        for(UserLocation userLocation : userLocationRepository.findByUserEntity(user))
        {

           userLocationList.add(userLocation.getCityName());

        }

       return userLocationList;


    }








}

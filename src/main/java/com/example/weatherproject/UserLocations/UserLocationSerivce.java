package com.example.weatherproject.UserLocations;

import com.example.weatherproject.User.UserEntity;
import com.example.weatherproject.User.UserRepository;
import com.example.weatherproject.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLocationSerivce {

    private final UserLocationRepository userLocationRepository;

    private final UserService userService;
    private final UserRepository userRepository;


    // SAVE

    public ResponseEntity<String> saveLocation(String username, String cityName{

        UserLocation userLocation = new UserLocation();
        userLocation.setCityName(cityName);
        userLocation.setUserEntity(userRepository.findByUsername(username).orElseThrow());
        userLocationRepository.save(userLocation);

        return ResponseEntity.ok(userLocation.getCityName() + "by" + userLocation.getUserEntity().getUsername());

    }



    // READ
    public ResponseEntity<String> readLocationsByUsername(String username){


        UserEntity user = userService.loadUserByUsername(username);



//      return ResponseEntity.ok(userLocations.getCityName());



        return ResponseEntity.ok("a");
    }






}

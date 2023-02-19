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

        UserEntity user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();

        user.setNumberOfCities(user.getNumberOfCities() + 1);

        userRepository.save(user);



        userLocationRepository.save(userLocation);

        return ResponseEntity.ok(userLocation.getCityName() + "by" + userLocation.getUserEntity().getUsername());

    }



    // READ - Read
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


    // UPDATE - Single Location

    public ResponseEntity<String> updateUserLocations(String username, String oldCity, String newCity){

        UserEntity user = userRepository.findByUsername(username).orElseThrow();



        for(UserLocation userlocation: userLocationRepository.findByUserEntity(user)){

            if(userlocation.getCityName().matches(oldCity)){
                userlocation.setCityName(newCity);
            }

            userLocationRepository.save(userlocation);

        }


        return ResponseEntity.ok("saved single location");
    }






    // DELETE

    public ResponseEntity<String> deleteUserLocation(String locationToRemove, String username){

        UserEntity user = userRepository.findByUsername(username).orElseThrow();

        List<UserLocation> userLocations = user.getUserLocations();

        UserLocation userLocationToRemove = new UserLocation();


        user.setNumberOfCities(user.getNumberOfCities() - 1);

        userRepository.save(user);


        // Find the userLocation
        for(UserLocation userlocation: userLocationRepository.findByUserEntity(user)){

            if(userlocation.getCityName().matches(locationToRemove)){
                userLocationToRemove = userlocation;
            }

        }



        userLocationRepository.delete(userLocationToRemove);


        return ResponseEntity.ok("Delete Location " + locationToRemove);

    }







}

package com.example.weatherproject.UserLocations;

import com.example.weatherproject.User.UserEntity;
import com.example.weatherproject.User.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserLocationController {

    private final UserLocationSerivce userLocationSerivce;
    private final UserRepository userRepository;

    //SAVE CONTROLLER
    @PostMapping("/api/v1/userlocation/new")
    @PreAuthorize("hasAuthority('USER')")

    public ResponseEntity<String> newUserLocation(@RequestBody String json) throws JsonProcessingException {

        System.out.println("reached saveLocationController");

        ObjectMapper objectMapper = new ObjectMapper();
      UserLocation userLocationRequest = objectMapper.readValue(json, UserLocation.class);


      userLocationSerivce.saveLocation(userLocationRequest);

        return ResponseEntity.ok("Saved userlocation");

    }


    // READ CONTROLLER

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/api/v1/userlocation/read")
    public ResponseEntity<List <UserLocationDTO>> readUserLocationByUsername(){

        System.out.println("reached location controller");



        UserEntity user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();


      return ResponseEntity.ok(userLocationSerivce.readLocationsByUsername(user.getUsername()));

    }



    //Update Controller
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/api/v1/userlocations/update")
    public ResponseEntity<String> updateUserLocation(@RequestBody String json) throws IOException {

        // Get user
        UserEntity user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();

        //Get the json cityname
        ObjectMapper objectMapper = new ObjectMapper();

     UserLocation userLocation = objectMapper.readValue(json, UserLocation.class);

     String cityName = userLocation.getCityName();

        userLocationSerivce.updateUserLocations(user.getUsername(), userLocation.getOldCity(), userLocation.getCityName());

        return ResponseEntity.ok("Updated locations with " + cityName);

    }



    //Delete Controller
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/api/v1/userlocations/delete")
    public ResponseEntity<ResponseEntity<String>> deleteLocation(@RequestBody String json) throws IOException{

        ObjectMapper objectMapper = new ObjectMapper();

        UserLocation userLocation = objectMapper.readValue(json, UserLocation.class);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(userLocationSerivce.deleteUserLocation(userLocation.getCityName(),username ));


    }









}

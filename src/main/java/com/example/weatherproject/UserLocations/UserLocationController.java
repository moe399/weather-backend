package com.example.weatherproject.UserLocations;

import com.example.weatherproject.User.UserEntity;
import com.example.weatherproject.User.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List <String>> readUserLocationByUsername(){

        System.out.println("reached location controller");

//        UserEntity user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();


        System.out.println("USERNAME " + SecurityContextHolder.getContext().getAuthentication().getName());

        UserEntity user = userRepository.findByUsername("user").orElseThrow();




      return ResponseEntity.ok(userLocationSerivce.readLocationsByUsername(user.getUsername()));

    }

}

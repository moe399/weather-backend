package com.example.weatherproject.UserLocations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserLocationController {

    private final UserLocationSerivce userLocationSerivce;

    //SAVE CONTROLLER

    @PostMapping("/api/v1/userlocation/new")
    @PreAuthorize("hasAuthority('USER')")

    public ResponseEntity<String> newUserLocation(@RequestBody String json) throws JsonProcessingException {


        ObjectMapper objectMapper = new ObjectMapper();
      UserLocation userLocationRequest = objectMapper.readValue(json, UserLocation.class);


      userLocationSerivce.saveLocation(userLocationRequest);

        return ResponseEntity.ok("Saved userlocation");

    }


}

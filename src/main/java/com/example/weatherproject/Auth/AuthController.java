package com.example.weatherproject.Auth;

import com.example.weatherproject.User.UserEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import  org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/v1/auth/register")
    public ResponseEntity<String> register(@RequestBody String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(json);
        JsonNode userLocationsNode = rootNode.get("userLocations");


      UserEntity requestUser = objectMapper.readValue(json, UserEntity.class);

      return ResponseEntity.ok(authService.Register(requestUser));



    }

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<String> authenticate(@RequestBody String json) throws IOException{

        ObjectMapper objectMapper = new ObjectMapper();
        UserEntity userRequest = objectMapper.readValue(json, UserEntity.class);

//        System.out.println(userRequest.getRole());

        if(authService.Authenticate(userRequest).matches("unable to authenticate")){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("unable to authenticate");
        }


        return ResponseEntity.ok(authService.Authenticate(userRequest));
    }

}

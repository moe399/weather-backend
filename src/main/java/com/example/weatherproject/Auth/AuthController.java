package com.example.weatherproject.Auth;

import com.example.weatherproject.User.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    public ResponseEntity<String> register(@RequestBody String json) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

      UserEntity requestUser = objectMapper.readValue(json, UserEntity.class);

      return ResponseEntity.ok("user saved");



    }


    public ResponseEntity<String> authenticate(){
        

    }

}

package com.example.weatherproject;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControllers {


    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<String> testController(){


        return ResponseEntity.ok("Accessed secured controller");


    }


}

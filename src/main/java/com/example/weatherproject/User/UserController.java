package com.example.weatherproject.User;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/api/v1/user")
    public ResponseEntity<UserEntity> readUser(){

        return ResponseEntity.ok(userService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));

    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/api/v1/user/update")
    public ResponseEntity<String> updateUser(@RequestBody String json) throws JsonProcessingException {
        //Get current user
        UserEntity user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();

        ObjectMapper objectMapper = new ObjectMapper();

        UserEntity updatedUserDetails = objectMapper.readValue(json, UserEntity.class);

        user.setUsername(updatedUserDetails.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(updatedUserDetails.getPassword()));

        userService.updateUser(user);

        return ResponseEntity.ok("User Updated");
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/api/v1/user/delete")
    public ResponseEntity<String> deleteUser(){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        userService.deleteUser(username);

        return ResponseEntity.ok("User Deleted");
    }


}

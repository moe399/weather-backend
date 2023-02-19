package com.example.weatherproject.User;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;


    // Read & Load
    @RequestMapping("/api/v1/user")
    @Override
    public UserEntity loadUserByUsername(String username){

     return  userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Username not found with repo"));

    }


    // Update
    public void updateUser(UserEntity updatedUser){

        userRepository.save(updatedUser);

        ResponseEntity.ok("Updated user");

    }






    public void deleteUser(String username){

        userRepository.delete(userRepository.findByUsername(username).orElseThrow());

    }





}

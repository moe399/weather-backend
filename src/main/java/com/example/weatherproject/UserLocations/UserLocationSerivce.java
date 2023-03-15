package com.example.weatherproject.UserLocations;

import com.example.weatherproject.User.UserEntity;
import com.example.weatherproject.User.UserRepository;
import com.example.weatherproject.User.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLocationSerivce {

    private final UserLocationRepository userLocationRepository;

    private final UserService userService;
    private final UserRepository userRepository;


    // SAVE

    public ResponseEntity<String> saveLocation(UserLocation userLocation) throws JsonProcessingException {


        userLocation.setUserEntity(userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow());

        UserEntity user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow();



        final String uri = "http://api.openweathermap.org/geo/1.0/direct?q=" + userLocation.getCityName()+ "&limit=1&appid=" + System.getenv("WEATHER_API");


        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        ObjectMapper objectMapper = new ObjectMapper();

        LocationLatLong[] locationArray = objectMapper.readValue(result, LocationLatLong[].class);
        LocationLatLong locationLatLong = locationArray[0];


        userLocation.setLatitude(locationLatLong.getLat());
        userLocation.setLongitude(locationLatLong.getLon());





        System.out.println(locationLatLong.getLat());
        System.out.println(locationLatLong.getCountry());


        System.out.println(result);




        user.setNumberOfCities(user.getNumberOfCities() + 1);

        userRepository.save(user);





        userLocationRepository.save(userLocation);

        return ResponseEntity.ok(userLocation.getCityName() + "by" + userLocation.getUserEntity().getUsername());


    }



    // READ - Read
//    public List<UserLocationDTO> readLocationsByUsername(String username){
//
//
//        UserEntity user = userService.loadUserByUsername(username);
//        System.out.println("Reached user list service location");
//
//        List<UserLocation> userLocationList = new ArrayList<>();
//
//        for (UserLocation userLocation : userLocationRepository.findByUserEntity(user)) {
//            UserLocationDTO userLocationDTO = new UserLocationDTO(
//                    userLocation.getCityName(),
//                    userLocation.getLatitude(),
//                    userLocation.getLongitude()
//            );
//
//
//            userLocationList.add(userLocationDTO);
//        }
//       return userLocationList;
//
//
//    }


    public List<UserLocationDTO> readLocationsByUsername(String username) {
        UserEntity user = userService.loadUserByUsername(username);
        List<UserLocationDTO> userLocationList = new ArrayList<>();

        for (UserLocation userLocation : userLocationRepository.findByUserEntity(user)) {
            UserLocationDTO userLocationDto = new UserLocationDTO(
                    userLocation.getCityName(),
                    userLocation.getLatitude(),
                    userLocation.getLongitude(),
                    userLocation.getCountryCode()
            );
            userLocationList.add(userLocationDto);
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

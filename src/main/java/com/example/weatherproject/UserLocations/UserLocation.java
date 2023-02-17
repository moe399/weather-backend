package com.example.weatherproject.UserLocations;

import com.example.weatherproject.User.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
//@Table(name = "User_Locations")
@RequiredArgsConstructor
public class UserLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String cityName;
    private String Longitude;
    private String latitude;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username")
    private UserEntity userEntity;



    public UserLocation(String cityName) {
        this.cityName = cityName;
    }


    // lat and long is set when a userlocation is added

}

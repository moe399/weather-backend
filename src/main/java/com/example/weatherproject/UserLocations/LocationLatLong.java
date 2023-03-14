package com.example.weatherproject.UserLocations;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;


@Getter
@Setter
public class LocationLatLong {


    private String name;
    private Map<String, String> local_names;
    private double lat;
    private double lon;
    private String country;
    private String state;


}

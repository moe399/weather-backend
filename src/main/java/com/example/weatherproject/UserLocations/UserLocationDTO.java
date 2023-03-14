package com.example.weatherproject.UserLocations;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLocationDTO {

    @JsonProperty("cityname")
    private String cityName;
    private Double Longitude;
    private Double latitude;

    @JsonCreator
    public UserLocationDTO(@JsonProperty("cityname") String cityName,
                           @JsonProperty("Longitude") Double longitude,
                           @JsonProperty("latitude") Double latitude) {
        this.cityName = cityName;
        this.Longitude = longitude;
        this.latitude = latitude;
    }
}
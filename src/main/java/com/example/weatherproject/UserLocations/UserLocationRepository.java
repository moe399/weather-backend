package com.example.weatherproject.UserLocations;

import com.example.weatherproject.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLocationRepository extends JpaRepository<UserLocation,Long > {


//    Optional<UserLocation> findByUsername(String username);

    List<UserLocation> findByUserEntity(UserEntity userEntity);






}

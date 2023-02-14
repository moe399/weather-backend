package com.example.weatherproject.Config;

import com.example.weatherproject.User.UserRepository;
import com.example.weatherproject.User.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtService {

    private final UserService userService;


    public String extractUsername(String token){

        Claims claims = Jwts.parser()
                .setSigningKey(System.getenv("SECRET_KEY"))
                .parseClaimsJws(token)
                .getBody();


        return claims.toString();
    }



    public String generateToken(String username){

    String token = Jwts.builder().setSubject(username)
                .signWith(SignatureAlgorithm.HS256, System.getenv("SECRET_KEY"))
                .compact();

    return token;
    }


    public boolean validate(String token){

        String tokenUsername = extractUsername(token);
        // check token is valid
        try{
            Jwts.parser().parseClaimsJws(token);
            try{
                userService.loadUsername(tokenUsername);
            }
            catch (Exception e){

                System.out.println("Username not found in validation method in JWT service");

            }
            return true;

        }

        catch (Exception e){
            return false;
        }


        // check if user exists in db
        // try catch block


    }


}

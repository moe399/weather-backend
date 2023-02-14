package com.example.weatherproject.Config;

import com.example.weatherproject.User.UserRepository;
import com.example.weatherproject.User.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtService {

    private final UserService userService;



    public String extractUsername(String token){

        Claims claims = Jwts.parser()
                .setSigningKey(System.getenv("SECRET_KEY"))
                .parseClaimsJws(token)
                .getBody();


        return claims.getSubject().toString();
    }



    public String generateToken(String username){

    String token = Jwts.builder().setSubject(username)
                .signWith(SignatureAlgorithm.HS256, System.getenv("SECRET_KEY"))
                .compact();

    return token;
    }


    public boolean validate(String token){

        final String reqUsername = Jwts.parser()
                .setSigningKey(System.getenv("SECRET_KEY"))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        // check token is valid
        System.out.println("reached validate token begin");

        try{
            Jwts.parser().setSigningKey(System.getenv("SECRET_KEY")).parseClaimsJws(token);

            userService.loadUserByUsername(reqUsername);

            System.out.println("jwt is valid");

            return true;

        }

        catch (Exception e){
            System.out.println();
            return false;
        }


        // check if user exists in db
        // try catch block


    }


}

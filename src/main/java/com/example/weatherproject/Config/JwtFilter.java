package com.example.weatherproject.Config;


import com.example.weatherproject.User.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String header = request.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")){

            filterChain.doFilter(request, response);
            return;


        }

        String jwt = header.substring(7);



        String reqUsername = jwtService.extractUsername(jwt);
        UserDetails userDetails = userService.loadUserByUsername(reqUsername);

//        System.out.println(reqUsername);

        if(jwtService.validate(jwt) == true){


            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println(SecurityContextHolder.getContext().getAuthentication());
        }
        else{
            System.out.println("couldnt validate token");
        }


        filterChain.doFilter(request, response);

    }
}

package com.ecomApp.userService.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtAuthenticationHelper authenticationHelper;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");

        String username = "";
        String token = "";

        if(requestHeader != null && requestHeader.contains("Bearer")){
            token = requestHeader.substring(7);
            System.out.println("token found: "+token);
            username = authenticationHelper.getUsernameFromToken(token);
            System.out.println("username found: "+username);

            if(username != null && SecurityContextHolder.getContext().getAuthentication() ==null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                System.out.println("UserDetails: "+userDetails);

                if(!authenticationHelper.isTokenExpired(token)){
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(token, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }
        }

        filterChain.doFilter(request, response);
    }

//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request){
//        String path = request.getServletPath();
//        return path.startsWith("/api/auth") || path.equals("/api/user/register");
//    }


}

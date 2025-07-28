package com.ecomApp.userService.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationHelper {

    private final String secretKey = "thisthesceretkeyformyownecomappthisthesceretkeyformyownecomappthisthesceretkeyformyownecomapp";
    private final long JSON_TOKEN_VALIDITY = 60*60;

    public String getUsernameFromToken(String token) {
        Claims claims = this.getClaimsForToken(token);
        return claims.getSubject();
    }

    private Claims getClaimsForToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey.getBytes())
                .build().parseClaimsJws(token).getBody();
    }

    public Boolean isTokenExpired(String token){
        Claims claim = this.getClaimsForToken(token);

        Date expDate = claim.getExpiration();
        return expDate.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JSON_TOKEN_VALIDITY*1000))
                .signWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS512.getJcaName()), SignatureAlgorithm.HS512)
                .compact();

    }
}

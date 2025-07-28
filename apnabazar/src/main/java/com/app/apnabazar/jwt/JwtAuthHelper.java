package com.app.apnabazar.jwt;

import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

@Component
public class JwtAuthHelper {

    private final String secretKey = "thisthesceretkeyformyownecomappthisthesceretkeyformyownecomappthisthesceretkeyformyownecomapp";

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

    public String getRole(String token) {
        return getClaimsForToken(token).get("role", String.class);
    }
}

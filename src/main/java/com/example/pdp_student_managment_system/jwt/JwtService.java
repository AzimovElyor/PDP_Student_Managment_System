package com.example.pdp_student_managment_system.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret-key}")
    private  String SECRET_KEY;
    @Value("${jwt.expired}")
    private Long expired;
    public String generateToken(UserDetails userDetails){
        Date date = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(date)
                .setExpiration(new Date(System.currentTimeMillis() + expired))
                .signWith(getSignInKey())
                .addClaims(getRoles(userDetails))
                .compact();
    }
    public Claims extraAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String getUsernameInToken(String token){
        Claims claims = extraAllClaims(token);
        return claims.getSubject();
    }
    private Date getExpirationDate(Claims claims){
        return claims.getExpiration();
    }

    private Map<String, Object> getRoles(UserDetails userDetails){
       return Map.of("roles",
                userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );

    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean isTokenValid(String token){
        Claims claims = extraAllClaims(token);
        return getExpirationDate(claims).after(new Date(System.currentTimeMillis()));
    }
}

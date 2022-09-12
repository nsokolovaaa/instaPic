package com.example.instapic.security;

import com.example.instapic.Entity.Users;
import io.jsonwebtoken.*;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class JWTTokenProvider {
    private static final org.slf4j.Logger Log =  LoggerFactory.getLogger(JWTTokenProvider.class);
    public String generateToken(Authentication authentication) {
        Users user = (Users) authentication.getPrincipal();
        Date now = new Date(System.currentTimeMillis());
        Date exp = new Date(now.getTime() +SecurityConstant.EXPIRATION_PLAN);

        String userId = Long.toString(user.getId());
        Map<String, Object> classMap = new HashMap<>();
        classMap.put("id", userId);
        classMap.put("userName", user.getUsername());
        classMap.put("userFirstName", user.getName());
        classMap.put("userLastName", user.getLastName());
        return Jwts.builder()
                .setSubject(userId)
                .addClaims(classMap)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, SecurityConstant.SECRET)
                .compact();


        }
        public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(SecurityConstant.SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch ( MalformedJwtException |
                 ExpiredJwtException
                | UnsupportedJwtException |
                 IllegalArgumentException e) {
            Log.error(e.getMessage());
            return false;

        }
        }
        public long getUserToken (String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstant.SECRET)
                .parseClaimsJws(token)
                .getBody();
        String id = (String) claims.get("id");
        return Long.parseLong(id);


        }
    }



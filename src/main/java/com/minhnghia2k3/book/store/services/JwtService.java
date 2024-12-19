package com.minhnghia2k3.book.store.services;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    String generateToken(UserDetails userDetails);
    String generateToken(Map<String,Object> extraClaims, UserDetails userDetails);
    Date getExpirationTime(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}

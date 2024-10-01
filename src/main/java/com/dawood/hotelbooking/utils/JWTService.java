package com.dawood.hotelbooking.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
  private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
  private final String SECRET_KEY = "E9rf7QYEN2PiwNSF4H33qE6HUwyQb5xTmNmCdae5p5lqVfGCxAZxSVTH5Z+pJPmN";

  private SecretKey generateSigningKey(){
    byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    return new SecretKeySpec(keyBytes,"HmacSHA256");
  }


  public String generateToken (UserDetails userDetails){
    return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
        .signWith(generateSigningKey())
        .compact();
 }

 public String extractUsername(String token){
    return extractClaims(token, Claims::getSubject);
 }

 public boolean isTokenValid(String token, UserDetails userDetails){
    return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
 }

 public boolean isTokenExpired(String token){
    return extractClaims(token,Claims::getExpiration).before(new Date());
 }


 private <T> T extractClaims(String token, Function<Claims, T> claimResolver){
  return claimResolver.apply(Jwts.parser().verifyWith(generateSigningKey()).build().parseSignedClaims(token).getPayload());
 }



}

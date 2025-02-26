package com.notesAPP.NotesAPP.Impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.internal.Function;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    String secretKey = "";



    public JWTService(){
        try {
            KeyGenerator sk=KeyGenerator.getInstance("HmacSHA256");
            secretKey =Base64.getEncoder().encodeToString(sk.generateKey().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String userName){

        Map<String,Object> claims = new HashMap<>();

     return Jwts.builder()
             .claims()
             .add(claims)
             .subject(userName)
             .issuedAt(new Date(System.currentTimeMillis()))
             .expiration(new Date(System.currentTimeMillis()+1000*60*60))
             .and()
             .signWith(getKey())
             .compact();
    }

    private SecretKey getKey() {
        byte[] keys= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keys);

    }

    public boolean validateToken(String token, UserDetails userdetails) {
        final String username = extractUserName(token);
        return (username.equals(userdetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpireTime(token).before(new Date());
    }

    private Date extractExpireTime(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}

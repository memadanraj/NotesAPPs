package com.notesAPP.NotesAPP.Impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.SystemException;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    String secretkey= "";
    public JWTService(){
        try {
            KeyGenerator sk=KeyGenerator.getInstance("HmacSHA256");
            secretkey=Base64.getEncoder().encodeToString(sk.generateKey().getEncoded());
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
             .expiration(new Date(System.currentTimeMillis()+60*60))
             .and()
             .signWith(getkey())
             .compact();
    }

    private Key getkey() {
        byte[] keys= Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keys);

    }
}

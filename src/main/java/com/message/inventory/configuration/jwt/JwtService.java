package com.message.inventory.configuration.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Builder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
@Builder
public class JwtService {
    private static String secretKey;

    //This constructor assign secret key by autowired rather than not by creating obj
    //because every time when we use new key keyword that time it will generate another secret key
    //algorithm :
    //AES (128)
    //DESede (168)
    //HmacSHA1
    //HmacSHA256
    public JwtService() {
        try {
            //Get instance of specific key generator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            //Generation of key by instance
            SecretKey secretKey1 = keyGenerator.generateKey();
            //encode character of secret ket in Base64 formatter
            secretKey = Base64.getEncoder().encodeToString(secretKey1.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    //that generate jwt token
    public String generateToken(String email) {
        HashMap<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date((System.currentTimeMillis()+3600000)))
                .and()
                .signWith(getSecretKey())
                .compact();
    }
    //create secure key
    private Key getSecretKey() {
        //secretKey = "SahajOrCode";
        byte[] keyBytes = Decoders.BASE64.decode((CharSequence) secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        String email = userDetails.getUsername();
        return (username.equals(email)&& !isTokenExpired(token));
    }
    private boolean isTokenExpired(String token) {
       return Jwts.parser()
                   .verifyWith((SecretKey) getSecretKey())
                   .build()
                   .parseSignedClaims(token)
                   .getPayload()
                   .getExpiration()
               .before(new Date());
    }


    //Old way to gathering claims from token
//    private <T> T extractClaim(String token, Function<Claims,T> claimResolver){
//        final Claims claims = extractAllClaims(token);
//        return claimResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parser()
//                .verifyWith((SecretKey) getSecretKey())
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
//    private Date extractExpiration(String token){
//        return extractClaim(token, Claims::getExpiration);
//    }
}

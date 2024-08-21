package com.message.inventory.SecurityService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
public class JWTService {
    private static String secretKey;
    public JWTService(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey1 = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(secretKey1.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String email) {
        HashMap<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date((System.currentTimeMillis() * 60 * 60 * 30)))
                .and()
                .signWith(getSecretKey())
                .compact();
    }

    private Key getSecretKey() {
//        secretKey = "SahajOrCode";
        byte[] keyBytes = Decoders.BASE64.decode((CharSequence) secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

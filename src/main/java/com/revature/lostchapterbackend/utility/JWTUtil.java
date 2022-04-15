package com.revature.lostchapterbackend.utility;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTUtil {
    
    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(String username) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject("User Details")
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer("revature")
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndGetUsername(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                                    .withSubject("User Details")
                                    .withIssuer("revature")
                                    .build();
        
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("username").asString();
    }

    public boolean checkCorrectFormat(String s) throws Exception {
        if (s == null || s.isBlank())
            throw new Exception("No valid token.");
        if (!s.startsWith("Bearer "))
            throw new Exception("Invalid token format.");
        return true;
    }
}

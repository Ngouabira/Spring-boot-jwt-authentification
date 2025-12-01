package com.ecpi.jwt.Configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
@ConfigurationProperties("app")
public class AppVariable {

    private String jwtSecret;
    private int jwtExpirationMs;


    public Key getKey(){
        byte[] keyBytes = Keys.hmacShaKeyFor(this.getJwtSecret().getBytes(StandardCharsets.UTF_8)).getEncoded();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public AppVariable(String jwtSecret, int jwtExpirationMs) {
        this.jwtSecret = jwtSecret;
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public AppVariable() {
    }

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public int getJwtExpirationMs() {
        return jwtExpirationMs;
    }

    public void setJwtExpirationMs(int jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
    }
}

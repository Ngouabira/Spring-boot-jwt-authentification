package com.ecpi.jwt.security;

import com.ecpi.jwt.Configuration.AppVariable;
import com.ecpi.jwt.model.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final AppVariable appVariable;

    public JwtService(AppVariable appVariable) {
        this.appVariable = appVariable;
    }

    public String generateAccessToken (Authentication authentication, int duration){
        return generateJwtToken(authentication, appVariable.getJwtExpirationMs());
    }

    public String generateRefreshToken (Authentication authentication, int duration){
        return generateJwtToken(authentication, 2 * appVariable.getJwtExpirationMs());
    }


    private String generateJwtToken(Authentication authentication, int duration){

        User user = (User)  authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date((System.currentTimeMillis() + duration + 60 * 60 * 1000)))
                .signWith(appVariable.getKey(), SignatureAlgorithm.HS256)
                .claim("authorities", user.getRole())
                .compact();
    }

    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(appVariable.getKey())
                .build().parseClaimsJws(token).getBody().getSubject();
    }


    public Date getExpirationDateFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(appVariable.getKey())
                .build().parseClaimsJws(token).getBody().getExpiration();
    }

    public boolean validateJwtToken(String token){

        try{
            Jwts.parserBuilder()
                    .setSigningKey(appVariable.getKey())
                    .build().parseClaimsJws(token);
            return true;
        }
        catch (SignatureException e) {
            throw new SignatureException( e.getMessage());
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException( e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(null, null, e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


}

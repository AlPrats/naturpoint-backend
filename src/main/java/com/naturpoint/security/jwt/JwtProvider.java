package com.naturpoint.security.jwt;

import com.naturpoint.security.model.MainUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiration;

    public String generateToken(Authentication auth) {
        MainUser mainUser = (MainUser) auth.getPrincipal();
        return Jwts.builder().setSubject(mainUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000L))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            ex.printStackTrace();
            logger.error("Malformed token");
        } catch (UnsupportedJwtException ex) {
            ex.printStackTrace();
            logger.error("Token not supported");
        } catch (ExpiredJwtException ex) {
            ex.printStackTrace();
            logger.error("Expirated token");
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            logger.error("Empty token");
        } catch (SignatureException ex) {
            ex.printStackTrace();
            logger.error("Signature not valid");
        }
        return false;
    }
}

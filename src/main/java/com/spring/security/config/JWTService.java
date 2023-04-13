package com.spring.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String SECRET_KEY = "6250655368566D5970337336763979244226452948404D635166546A576E5A72";
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token){
        return Jwts.
                parserBuilder().
                setSigningKey(getSignInKey()).
                build().
                parseClaimsJws(token).
                getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
//signInKey is something used to digitally sign the jwt token. It is to ensure that the same person is sending the data and the data is not manipulated along the way
//Use this site to generate SECRET_KEY https://www.allkeysgenerator.com/Random/Security-Encryption-Key-Generator.aspx
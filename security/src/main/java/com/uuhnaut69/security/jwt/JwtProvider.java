package com.uuhnaut69.security.jwt;

import com.uuhnaut69.security.user.UserPrinciple;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;


/**
 * @author uuhnaut
 * @project mall
 */
@Slf4j
@Component
public class JwtProvider {

    private static final String AUTHORITIES_KEY = "auth";

    @Value("${ecommerce.jwtSecret}")
    private String jwtSecret;

    @Value("${ecommerce.jwtExpiration}")
    private int jwtExpiration;

    /**
     * Generate jwt token
     *
     * @param authentication
     * @return String
     */
    public String generateJwtToken(Authentication authentication) {
        UserPrinciple userPrincipal = (UserPrinciple) authentication.getPrincipal();
        String authorities = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    /**
     * Extract user name from jwt token
     *
     * @param token
     * @return String
     */
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validate jwt token
     *
     * @param authToken
     * @return boolean
     */
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            //noinspection PlaceholderCountMatchesArgumentCount
            log.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            //noinspection PlaceholderCountMatchesArgumentCount
            log.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            //noinspection PlaceholderCountMatchesArgumentCount
            log.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            //noinspection PlaceholderCountMatchesArgumentCount
            log.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            //noinspection PlaceholderCountMatchesArgumentCount
            log.error("JWT claims string is empty -> Message: {}", e);
        }
        return false;
    }
}

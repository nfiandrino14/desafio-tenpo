package com.tenpo.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tenpo.api.dto.AccessTokenDTO;
import static com.tenpo.api.filter.CustomAuthorizationFilter.BEARER;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    public static final long ACCESS_TOKEN_EXPIRATION = 15 * 60 * 1000;
    public static final String ROLES = "roles";
    public static final String SECRET_KEY = "jwt.secret.key";

    private final AuthenticationManager authenticationManager;
    private final UserLogedCache cache;

    @Autowired
    private Environment env;

    public Authentication authenticate(String username, String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            log.info("Username {} is not active", username);
            throw new DisabledException("User is not active");
        } catch (BadCredentialsException e) {
            log.info("Bad Credentials: username {} , password {}", username, password);
            throw new BadCredentialsException("Credentials are not valid");
        }
    }

    public AccessTokenDTO generateAccessToken(Authentication auth) {
        User user = (User) auth.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(env.getProperty(SECRET_KEY).getBytes());
        Long startTime = System.currentTimeMillis();
        String access_token = JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(new Date(startTime))
                .withExpiresAt(new Date(startTime + ACCESS_TOKEN_EXPIRATION))
                .withClaim(ROLES, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);

        log.info("[Log In Succesfull] Username {} loading in cache", user.getUsername());
        cache.addUser(access_token, user.getUsername());
        return new AccessTokenDTO(access_token);

    }

    public String invalidateAccessToken(String authHeader) {
        String token = authHeader.substring(BEARER.length());
        String username = getUsernameFromToken(token);
        if (cache.isUserOnCache(token)) {
            log.info("[Log out Succesfull] {} disconected and removing data from cache", username);
            cache.removeUser(token);
        }
        return username;
    }

    private String getUsernameFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(env.getProperty(SECRET_KEY).getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

}

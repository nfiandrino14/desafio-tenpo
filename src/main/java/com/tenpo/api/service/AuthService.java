package com.tenpo.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.tenpo.api.dto.AccessTokenDTO;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final AuthenticationManager authenticationManager;
    private final UserLogedCache cache;

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
        Algorithm algorithm = Algorithm.HMAC256("top-secret".getBytes());
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

}

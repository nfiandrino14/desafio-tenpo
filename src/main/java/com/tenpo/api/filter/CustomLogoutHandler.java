package com.tenpo.api.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import static com.tenpo.api.filter.CustomAuthorizationFilter.BEARER;
import com.tenpo.api.service.UserLogedCache;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final UserLogedCache cache;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication a) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            String token = authorizationHeader.substring(BEARER.length());
            Algorithm algorithm = Algorithm.HMAC256("top-secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String username = decodedJWT.getSubject();
            if (cache.isUserOnCache(token)) {
                log.info("[Log out Succesfull] {} disconected and removing data from cache", username);
                cache.removeUser(token);
            }
        }
    }
}

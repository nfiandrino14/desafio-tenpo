package com.tenpo.api.services;

import com.tenpo.api.service.AuthService;
import com.tenpo.api.service.UserLogedCache;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;

public class AuthServiceTest {

    private static final String USERNAME = "JhonUser";
    private static final String PASSWORD = "admin1234";

    private AuthenticationManager authenticationManager;
    private UserLogedCache cache;
    private AuthService service;

    @BeforeEach
    public void setUp() {
        cache = mock(UserLogedCache.class);
        authenticationManager = mock(AuthenticationManager.class);
        service = new AuthService(authenticationManager, cache);
    }

    @Test
    void authenticationUser() {
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(Boolean.TRUE);

        when(authenticationManager.authenticate(any())).thenReturn(auth);

        Authentication authentication = service.authenticate(USERNAME, PASSWORD);
        assertTrue(authentication.isAuthenticated());

    }

    @Test
    void authenticationUserBadCredentials() {
        when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

        Exception exception = assertThrows(BadCredentialsException.class, () -> service.authenticate(USERNAME, PASSWORD));

        Assertions.assertTrue(exception instanceof BadCredentialsException);
        assertEquals(exception.getMessage(), "Credentials are not valid");
    }

    @Test
    void authenticationUserDisabled() {
        when(authenticationManager.authenticate(any())).thenThrow(DisabledException.class);

        Exception exception = assertThrows(DisabledException.class, () -> service.authenticate(USERNAME, PASSWORD));

        Assertions.assertTrue(exception instanceof DisabledException);
        assertEquals(exception.getMessage(), "User is not active");
    }

}

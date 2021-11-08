package com.tenpo.api.services;

import com.tenpo.api.domain.User;
import com.tenpo.api.repository.UserRepository;
import com.tenpo.api.service.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceImplTest {

    private final static String USERNAME = "nico";
    private final static String PASSWORD = "1234";
    private final static String MAIL = "nico@tenpo.com";

    private UserServiceImpl service;
    private UserRepository repository;
    private PasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        repository = mock(UserRepository.class);
        encoder = mock(PasswordEncoder.class);
        service = new UserServiceImpl(repository, encoder);
    }

    @Test
    void userFound() {
        User user = new User(USERNAME, PASSWORD, MAIL);

        when(repository.findByUsername(USERNAME)).thenReturn(user);

        UserDetails result = service.loadUserByUsername(USERNAME);
        assertEquals(result.getUsername(), USERNAME);
        assertEquals(result.getPassword(), PASSWORD);

    }

    @Test
    void userNotFound() {
        User user = new User(USERNAME, PASSWORD, MAIL);

        when(repository.findByUsername(USERNAME)).thenReturn(null);

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername(USERNAME));

        Assertions.assertTrue(exception instanceof UsernameNotFoundException);
        assertEquals(exception.getMessage(), "User not found in database");

    }

    @Test
    void registerUser() {
        User userToSave = new User(USERNAME, PASSWORD, MAIL);

        when(encoder.encode(PASSWORD)).thenReturn(PASSWORD);
        when(repository.save(userToSave)).thenReturn(userToSave);

        User user = service.registerUser(userToSave);
        assertEquals(user.getUsername(), userToSave.getUsername());
        assertEquals(user.getEmail(), userToSave.getEmail());

    }

    @Test
    void isUserRegisterTrue() {
        User userFound = new User(USERNAME, PASSWORD, MAIL);

        when(repository.findByUsername(USERNAME)).thenReturn(userFound);

        boolean result = service.isUserRegistered(USERNAME);

        assertTrue(result);
    }

    @Test
    void isUserRegisterFalse() {
        when(repository.findByUsername(USERNAME)).thenReturn(null);

        boolean result = service.isUserRegistered(USERNAME);

        assertFalse(result);
    }

}

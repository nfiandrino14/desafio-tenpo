package com.tenpo.api.controller;

import com.tenpo.api.domain.User;
import com.tenpo.api.dto.UserDTO;
import com.tenpo.api.exception.UserAlreadyExistException;
import com.tenpo.api.service.UserService;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/singup")
    public ResponseEntity<User> singUpUser(@Valid @RequestBody UserDTO userDTO) throws UserAlreadyExistException {
        if (userService.isUserRegistered(userDTO.getUsername().trim().toLowerCase())) {
            throw new UserAlreadyExistException("User is already registered: " + userDTO.getUsername());
        }

        User _user = userService.registerUser(new User(userDTO.getUsername().trim().toLowerCase(), userDTO.getPassword(), userDTO.getMail()));

        return new ResponseEntity<>(_user, HttpStatus.CREATED);
    }

}

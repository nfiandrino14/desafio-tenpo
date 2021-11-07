package com.tenpo.api.controller;

import com.tenpo.api.domain.User;
import com.tenpo.api.dto.AccessTokenDTO;
import com.tenpo.api.dto.UserDTO;
import com.tenpo.api.dto.UserLoginDTO;
import com.tenpo.api.exception.UserAlreadyExistException;
import com.tenpo.api.service.AuthService;
import com.tenpo.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Api(tags = "Users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/singup")
    @ApiOperation(value = "User Register")
    public ResponseEntity<User> singUpUser(@ApiParam(value = "User to register", required = true) @Valid @RequestBody UserDTO userDTO) throws UserAlreadyExistException {

        if (userService.isUserRegistered(userDTO.getUsername().trim().toLowerCase())) {
            throw new UserAlreadyExistException("User is already registered: " + userDTO.getUsername());
        }

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/singup").toUriString());
        User user = new User(userDTO.getUsername().trim().toLowerCase(), userDTO.getPassword(), userDTO.getMail());

        return ResponseEntity.created(uri).body(userService.registerUser(user));
    }

    @PostMapping("/login")
    @ApiOperation(value = "User Login")
    public ResponseEntity<AccessTokenDTO> login(@ApiParam(value = "User Credentials", required = true) @Valid @RequestBody UserLoginDTO credentials) {
        Authentication auth = authService.authenticate(credentials.getUsername(), credentials.getPassword());
        return ResponseEntity.ok(authService.generateAccessToken(auth));
    }

}

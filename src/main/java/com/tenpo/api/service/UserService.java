package com.tenpo.api.service;

import com.tenpo.api.domain.User;

public interface UserService {

    User registerUser(User user);

    User getUserByUsername(String username);

    boolean isUserRegistered(String username);

}

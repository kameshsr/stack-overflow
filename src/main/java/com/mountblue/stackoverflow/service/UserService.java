package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.dto.UserRegistrationDto;
import com.mountblue.stackoverflow.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void saveUser(User user);

    boolean isValidUser(String userName, String password);

    User getUserByEmail(String email);

    User save(UserRegistrationDto registrationDto);
}

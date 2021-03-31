package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.User;

public interface UserService {

    void saveUser(User user);

    boolean isValidUser(String userName, String password);
}

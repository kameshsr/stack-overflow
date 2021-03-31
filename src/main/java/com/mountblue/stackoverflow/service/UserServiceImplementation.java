package com.mountblue.stackoverflow.service;

import com.mountblue.stackoverflow.model.User;
import com.mountblue.stackoverflow.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImplementation implements UserService{

    private UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public boolean isValidUser(String userName, String password) {
        List<User> users = userRepository.findAll();

        for(User user: users) {
            if (user.getName().equals(userName)
            && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}

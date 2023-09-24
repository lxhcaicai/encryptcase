package com.github.encryptcase.service;

import com.github.encryptcase.domain.User;

import java.util.List;

public interface UserService {
    void addUser(User user);

    void delUser(Integer id);

    void updateUser(User user);

    List<User> findAll();

    User findById(Integer id);
}

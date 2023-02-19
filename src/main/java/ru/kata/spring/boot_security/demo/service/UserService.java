package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void addUser(User user);
    User getUser(long id);
    List<User> getUsers();
    void cleanTable();
    void deleteUser(long id);
    void updateUser(User user);
}

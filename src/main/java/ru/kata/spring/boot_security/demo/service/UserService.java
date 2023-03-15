package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Usr;

import java.text.ParseException;
import java.util.List;

public interface UserService {
    void addUser(String name, String lastName, String date, String password, String auth) throws ParseException;

    User getUser(long id);

    List<User> getUsers();

    void cleanTable();

    void deleteUser(long id);

    void updateUser(Long id, Usr user) throws ParseException;
}

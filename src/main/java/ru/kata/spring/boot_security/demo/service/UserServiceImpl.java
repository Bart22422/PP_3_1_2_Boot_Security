package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
@Service
public class UserServiceImpl implements  UserService{
    private final UserRepository userDaoImpl;
    @Autowired
    public UserServiceImpl(UserRepository userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    @Transactional
    public void addUser(User user) {userDaoImpl.save(user);
    }

    @Override
    @Transactional
    public User getUser(long id) {
        return userDaoImpl.getById(id);
    }
    @Override
    @Transactional
    public List<User> getUsers() {
        return userDaoImpl.findAll();
    }
    @Override
    @Transactional
    public void cleanTable() {
        userDaoImpl.deleteAll();
    }
    @Override
    @Transactional
    public void deleteUser(long id) {
        userDaoImpl.deleteById(id);
    }
    @Override
    @Transactional
    public void updateUser( User user){
        userDaoImpl.save(user);
    }
}

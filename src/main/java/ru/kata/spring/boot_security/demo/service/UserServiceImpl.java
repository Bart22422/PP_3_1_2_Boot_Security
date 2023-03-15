package ru.kata.spring.boot_security.demo.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Usr;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements  UserService{
    private RoleService roleService;
    private final UserRepository userDaoImpl;

    public UserServiceImpl(UserRepository userDaoImpl, RoleService roleService) {

        this.roleService = roleService;
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    @Transactional
    public void addUser(String name, String lastName, String date, String password, String auth) throws ParseException {
        User user1 = new User();
        if (!date.isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern("yyyy-MM-dd");
            Date date1 = simpleDateFormat.parse(date);
            user1.setAge(date1);
        }
        if (lastName != null) {
            user1.setLastName(lastName);
        }
        user1.setName(name);
        user1.setPassword(password);
        user1.addRole(roleService.loadByRoleName(auth));
        userDaoImpl.save(user1);
    }

    @Override
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
    public void updateUser( Long id, Usr user) throws ParseException {
        User oldUser = userDaoImpl.getById(id);
        String oldName = oldUser.getName();
        String oldLastName = oldUser.getLastName();
        Set<Role> oldRole = oldUser.getRoles();
        Date age = oldUser.getAge();

        if (user.getName() != null) {
            oldUser.setName(user.getName());
        } else {
            oldUser.setName(oldName);
        }
        if (user.getLastName() != null) {
            oldUser.setLastName(user.getLastName());
        } else {
            oldUser.setLastName(oldLastName);
        }
        if (user.getAuthority() != null) {
            if (!oldUser.getRoles().contains(user.getAuthority())){
                oldUser.addRole(roleService.loadByRoleName(user.getAuthority()));
            }
        } else {
            oldUser.setRoles(oldRole);
        }
        System.out.println(user.getDate());
        if (user.getDate() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern("yyyy-MM-dd");
            Date date1 = simpleDateFormat.parse(user.getDate());
            oldUser.setAge(date1);
        } else {
            oldUser.setAge(age);
        }
        userDaoImpl.save(oldUser);
    }
 
}

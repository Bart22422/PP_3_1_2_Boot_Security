package ru.kata.spring.boot_security.demo.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.LongId;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.model.Usr;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController("/")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/user")
    public User user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user;
    }

    @GetMapping("/users")
    public List<User> users() {
       /* Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();*/
        return userService.getUsers();
    }


    @GetMapping("/add")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @PostMapping("/form")
    @ResponseBody
    public void addNewUser(@RequestParam(value = "name") String name, @RequestParam(value = "password") String pass, @RequestParam(value = "lastName", required = false) String lastName, @RequestParam(value = "date", required = false, defaultValue = "0000-00-00") String date, @RequestParam(value = "authorisation", required = false, defaultValue = "ROLE_USER") String auth) throws ParseException, IOException {
        User user = new User();
        if (!date.isEmpty()) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern("yyyy-MM-dd");
            Date date1 = simpleDateFormat.parse(date);
            user.setAge(date1);
            user.setAge(date1);
        }
        if (lastName != null) {
            user.setLastName(lastName);
        }
        user.setName(name);
        user.setPassword(pass);
        System.out.println(auth);
        user.addRole(roleService.loadByRoleName(auth));
        System.out.println(user.getRoles());
        userService.addUser(user);
    }

    @PostMapping("/update/{id:\\d+}")
    @ResponseBody
    public void update(@PathVariable Long id, @RequestBody Usr usr /*@RequestParam(value = "name", required = false) String name, @RequestParam(value = "lastName", required = false) String lastName, @RequestParam(value = "date", required = false, defaultValue = "0000-00-00") String date, @RequestParam(value = "authorisation", required = false) String auth*/) throws ParseException {
        User user = userService.getUser(id);
        String oldName = user.getName();
        String oldLastName = user.getLastName();
        Set<Role> oldRole = user.getRoles();
        Date age = user.getAge();
        System.out.println(usr);
        if (usr.getName() != null) {
            user.setName(usr.getName());
        } else {
            user.setName(oldName);
        }
        if (usr.getLastName() != null) {
            user.setLastName(usr.getLastName());
        } else {
            user.setLastName(oldLastName);
        }
        if (usr.getAuthority() != null) {
            user.addRole(roleService.loadByRoleName(usr.getAuthority() ));
        } else {
            user.setRoles(oldRole);
        }
        if (usr.getDate() != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            simpleDateFormat.applyPattern("yyyy-MM-dd");
            Date date1 = simpleDateFormat.parse(usr.getDate());
            user.setAge(date1);
        } else {
            user.setAge(age);
        }
        userService.updateUser(user);
    }

    @DeleteMapping("/delete/{id:\\d+}")
    public void delete( @PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/clean")
    public void clean() {
        userService.cleanTable();
    }
}

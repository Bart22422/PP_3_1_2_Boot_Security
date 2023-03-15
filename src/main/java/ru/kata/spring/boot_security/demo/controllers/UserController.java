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
    public void addNewUser(@RequestParam(value = "name") String name, @RequestParam(value = "password") String pass, @RequestParam(value = "lastName", required = false) String lastName, @RequestParam(value = "date", required = false, defaultValue = "0000-00-00") String date, @RequestParam(value = "authorisation", required = false, defaultValue = "ROLE_USER") String auth) throws ParseException {
        userService.addUser(name, lastName, date, pass, auth);
    }

    @PostMapping("/update/{id:\\d+}")
    @ResponseBody
    public void update(@PathVariable Long id, @RequestBody Usr usr) throws ParseException {
        userService.updateUser(id, usr);
    }

    @DeleteMapping("/delete/{id:\\d+}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PostMapping("/clean")
    public void clean() {
        userService.cleanTable();
    }
}

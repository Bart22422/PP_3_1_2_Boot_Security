package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
public class LoginController {
    @Autowired
    UserService userService;

    @GetMapping("/modern-login-page")
    public String login() {
        return "modern-login-page";
    }
    @GetMapping("/admin")
    public String admin (Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        List<User> userList =  userService.getUsers();
        model.addAttribute("us", user);
        model.addAttribute("users", userList);
        return "users";
    }
}
